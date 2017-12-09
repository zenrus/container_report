package zenrus.com.container.persistance;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import zenrus.com.container.beans.InputBean;
import zenrus.com.container.beans.OutputBean;
import zenrus.com.container.beans.Train;
import zenrus.com.container.exception.DbException;
import zenrus.com.container.report.Environment;

public class HibernateControl {

	private static final Logger LOG = LogManager.getLogger( HibernateControl.class );
	
	public static void saveAll(List<InputBean> beans) throws DbException{
		Transaction transaction = null;		
		try{		
			Session session = Environment.getInstance().getSession();
			transaction = session.beginTransaction();
			for(InputBean bean : beans){
				LOG.debug(bean);
				session.save( bean );
			}
			session.flush();
			transaction.commit();
		}catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			LOG.error(e);
			throw new DbException(e.getMessage());
		}
		
	}
	
	
	public static List<Train> getTrains() throws DbException{
		Transaction transaction = null;
		List<Train> trains = new ArrayList<Train>();
		 try{
			 
			List<String> trainsNumbers = getTrainsNumber();
			Session session = Environment.getInstance().getSession();
			transaction = session.beginTransaction();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<OutputBean> query = builder.createQuery(OutputBean.class);
			Root<OutputBean> root = query.from(OutputBean.class);
			
			for(String trainsNumber : trainsNumbers) {
				Train train = new Train();
				train.setTitle(trainsNumber);
				LOG.debug("Поезд №" + trainsNumber);				
				query.select(root).where(builder.equal(root.get("titleTrain"),trainsNumber));
				Query<OutputBean> q = session.createQuery(query);
				List<OutputBean> beans = q.getResultList();
				train.setContainers(beans);
				for(OutputBean bean : beans){
					train.setIndex(bean.getIndexTrain());
					LOG.debug(bean.getNumber() +" "+ bean.getContainerNumber());
				}
				trains.add(train);
			}
			session.flush();
			transaction.commit();
		}catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			LOG.error(e);
			throw new DbException(e.getMessage());
		}
		
		return trains;
	}
	
	public static List<String> getTrainsNumber() throws DbException{
		List<String> result = new ArrayList<String>();
		Transaction transaction = null;
		 try{
			Session session = Environment.getInstance().getSession();
			transaction = session.beginTransaction();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<String> query = builder.createQuery(String.class);
			Root<InputBean> root = query.from(InputBean.class);
			query.select(root.get("titleTrain")).distinct(true);
			Query<String> q = session.createQuery(query);
			List<String> trains = q.getResultList();
			result.addAll(trains);
			LOG.debug(trains);
			session.flush();
			transaction.commit();
		}catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			LOG.error(e);
			throw new DbException(e.getMessage());
		}
		
		return result;
	}
	
}
