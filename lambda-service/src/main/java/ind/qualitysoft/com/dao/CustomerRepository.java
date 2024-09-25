package ind.qualitysoft.com.dao;

import ind.qualitysoft.com.model.CustomerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CustomerRepository {

    @Autowired private JdbcClient jdbcClient;

  public List<CustomerInfo> fetchCustomersInfo() {

      String sqlQuery = """
                 select
                   c.id as customerId,
                   concat(c.first_name, ' ', c.last_name) as name,
                   o.id as orderId,
                   o.product_name,
                   o.quantity,
                   o.amount,
                   o.cancelled_at as cancelledTimestamp
                 from
                   ecommerce.customers as c,
                   ecommerce.orders as o
                 where
                   c.id = o.customer_id
                   and o.status = 'cancelled'
                 order by
                   c.id
              """;
      return jdbcClient.sql(sqlQuery).query(CustomerInfo.class).list();
  }
}
