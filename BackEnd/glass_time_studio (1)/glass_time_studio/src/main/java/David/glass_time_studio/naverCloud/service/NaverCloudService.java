package David.glass_time_studio.naverCloud.service;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NaverCloudService {

    private final AmazonS3 s3Client;
    @Value("${navercloud.bucketName}")
    private String bucketName;

    public NaverCloudService(@Value("${navercloud.accessKey}") String accessKey,
                             @Value("${navercloud.secretKey}") String secretKey,
                             @Value("${navercloud.region}") String region){
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);

        String endpoint = "https://kr.object.ncloudstorage.com";
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withPathStyleAccessEnabled(true)
                .build();

        log.info("N_Access_Key: "+accessKey);
        log.info("N_Secret_Key: "+secretKey);
        log.info("N_Region: "+region);
    }
    public List<String> uploadFiles(List<MultipartFile> files) throws IOException {
        return files.stream()
                .map(file -> {
                    try {
                        return uploadFile(file);
                    }catch (IOException e){
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    public String uploadFile(MultipartFile file) throws IOException{
        File tempFile = convertMultipartFileToFile(file);
        String fileName = generateFileName(file.getOriginalFilename());
        String objectKey = "product/" + fileName;

        s3Client.putObject(new PutObjectRequest(bucketName, objectKey, tempFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        tempFile.delete();

        String fileUrl = s3Client.getUrl(bucketName, objectKey).toString();

        log.info("(uploadFile) 사진 url: "+fileUrl);
        // 여기에 productRepository에 저장하도록 코드 작성 예정.
        return fileUrl;
    }
    private File convertMultipartFileToFile(MultipartFile file) throws IOException{
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
    private String generateFileName(String originalFileName){
        String dateFormat = new SimpleDateFormat("yyyyMMdd").format(new Date());
        return dateFormat + "_" + originalFileName;
    }
}
