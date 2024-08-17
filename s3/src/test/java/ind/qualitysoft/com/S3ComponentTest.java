package ind.qualitysoft.com;

import ind.qualitysoft.com.component.S3Component;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.services.s3.S3Client;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class S3ComponentTest {

  @Autowired private S3Client s3Client;

  private S3Component s3Component;

  @BeforeEach
  void setUp() {
    s3Component = new S3Component(s3Client);
  }

  @Test
  void shouldListFiles() {
    final var bucketFiles = s3Component.listBucketFiles("test-bucket", "test-prefix/");
    assertThat(bucketFiles).isNotEmpty().hasSize(4);
  }
}
