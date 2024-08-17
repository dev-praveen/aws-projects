package ind.qualitysoft.com.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.S3Object;
import java.util.List;

@Component
@RequiredArgsConstructor
public class S3Component {

  private final S3Client s3Client;

  public List<String> listBucketFiles(
      @Value("${s3.bucket.name}") String bucketName, @Value("${s3.bucket.prefix}") String prefix) {

    final var listObjectsV2Request =
        ListObjectsV2Request.builder().bucket(bucketName).prefix(prefix).delimiter("/").build();

    final var listObjectsV2Response = s3Client.listObjectsV2(listObjectsV2Request);
    return listObjectsV2Response.contents().stream().map(S3Object::key).toList();
  }
}
