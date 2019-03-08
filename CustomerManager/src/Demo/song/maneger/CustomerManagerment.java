package Demo.song.maneger;

import java.util.Iterator;
import java.util.List;

import javax.imageio.spi.ServiceRegistry;

import org.apache.commons.collections.Factory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import Demo.song.entity.Customer;

/**
 * Hello world!
 *
 */
public class CustomerManagerment {
	private static SessionFactory factory;

	public static void main(String[] args) {
		try {
			factory = new AnnotationConfiguration().configure().addAnnotatedClass(Customer.class).buildSessionFactory();
		} catch (Throwable ex) {
			System.out.println("Failed to create Session factory");
			ex.printStackTrace();
		}
		CustomerManagerment cm = new CustomerManagerment();
//		cm.Listcustomer();
//		Integer c1 = cm.addCustomer(4, "Huy Phong", "phong@gmail.com", "0123456", "TPHCM");
//		cm.Listcustomer();
//		cm.UpdateCus(1, "a", "a", "aa");
//		cm.Listcustomer();
//		cm.delete(2);
		cm.Listcustomer();

	}

	// insert song to database
	public Integer addCustomer(int id, String name, String email, String phone, String address) {
		Session session = factory.openSession();
		Transaction trx = null;
		Integer cusid = null;
		try {
			trx = session.beginTransaction();
			Customer c = new Customer(id, name, email, phone, address);
			cusid = (Integer) session.save(c);
			trx.commit();
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return cusid;
	}

	// method read all song
	public void Listcustomer() {
		Session session = factory.openSession();
		Transaction trx = null;
		try {
			trx = session.beginTransaction();
			List cus = session.createQuery("from Customer").list();
			for (Iterator iterator = cus.iterator(); iterator.hasNext();) {
				Customer c = (Customer) iterator.next();
				System.out.println("ID:" + c.getId());
				System.out.println("Name:" + c.getName());
				System.out.println("Email:" + c.getEmail());
				System.out.println("Phone: " + c.getPhone());
				System.out.println("Address:" + c.getAddress());
			}
			trx.commit();
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	// update song description
	public void UpdateCus(Integer cusid, String email, String phone, String address) {
		Session session = factory.openSession();
		Transaction trx = null;
		try {
			trx = session.beginTransaction();
			Customer c = (Customer) session.get(Customer.class, cusid);
			c.setEmail(email);
			c.setAddress(address);
			c.setPhone(phone);
			session.update(c);
			trx.commit();
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	// delete a song
	public void delete(Integer cusid) {
		Session session = factory.openSession();
		Transaction trx = null;
		try {
			trx = session.beginTransaction();
			Customer c = (Customer) session.get(Customer.class, cusid);
			session.delete(c);
			trx.commit();
		} catch (HibernateException e) {
			if (trx != null)
				trx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}
