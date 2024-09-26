package ind.qualitysoft.com.service;

import ind.qualitysoft.com.dao.CustomerRepository;
import ind.qualitysoft.com.model.CustomerInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerCsvDataService {

  private static final String FILE_NAME = "Customers_cancelled_orders_list_";
  private final CustomerRepository customerRepository;

  public String constructCsvWithCustomersInfo() {

    final var customerInfoList = customerRepository.fetchCustomersInfo();
    log.info("Size of customer records from db: {}", customerInfoList.size());

    final String[] headers = {
      "customerId", "name", "orderId", "quantity", "amount", "cancelledTimestamp"
    };
    final var csvFormat = CSVFormat.DEFAULT.builder().setHeader(headers).build();
    final var generatedFile = FILE_NAME + LocalDate.now() + ".csv";
    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(generatedFile));
        CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat)) {

      for (CustomerInfo customerInfo : customerInfoList) {
        csvPrinter.printRecord(
            customerInfo.customerId(),
            customerInfo.name(),
            customerInfo.orderId(),
            customerInfo.quantity(),
            customerInfo.amount(),
            customerInfo.cancelledTimestamp());
      }
      csvPrinter.flush();
      log.info("Constructed CSV file with customer records");
    } catch (IOException e) {
      log.error("Exception occurred while constructing CSV file with customer records ", e);
    }
    return generatedFile;
  }
}
