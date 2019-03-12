package model;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class SolutionDataMapper {
	private static SessionFactory factory;

	public SolutionDataMapper() {
		Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		Configuration configuration = new Configuration();
		configuration.configure();
		factory = configuration.buildSessionFactory();
	}

	public boolean isExsit(int hashCode) {
		Session session = factory.openSession();
		Query<Long> q = session.createQuery("select count(*) From model.State s WHERE s.hashCode=:hashCode");
		q.setParameter("hashCode", hashCode);
		return q.uniqueResult() > 0;
	}

	public State getState(int hashCode) {

		Session session = factory.openSession();
		Query<State> stateQuery = session.createQuery("From model.State s WHERE s.hashCode=:hashCode");
		stateQuery.setParameter("hashCode", hashCode);
		State state = stateQuery.uniqueResult();
		if (state.getSolvedable()) {
			Query<Action> actionsQuery = session.createQuery("From model.Action a WHERE a.stateId=:hashCode ORDER BY a.index");
			actionsQuery.setParameter("hashCode", hashCode);
			List<Action> actions = actionsQuery.list();
			Action[] actionsArr = new Action[actions.size()];
			actions.toArray(actionsArr);
			state.setActions(actionsArr);
		}
		return state;
	}

	public void insertState(State state) {
		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(state);
			for (Action a : state.getActions()) {
				session.save(a);
			}
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
}
