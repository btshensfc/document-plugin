package document.plugin

import grails.testing.services.ServiceUnitTest
import grails.testing.mixin.integration.Integration
import spock.lang.Specification
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.BasicDBObject
import org.bson.Document
import org.bson.types.ObjectId

@Integration
class UploadFileServiceSpec extends Specification implements ServiceUnitTest<UploadFileService>{

    MongoClient mongoClient
    MongoDatabase database
    MongoCollection<Document> collection

    def setup() {
        // Initialize MongoDB client and database
        mongoClient = MongoClients.create("mongodb://localhost:27017/")
        database = mongoClient.getDatabase("teachershop")
        collection = database.getCollection("files")

        // Optional: Insert initial test data if needed
        //Document testDocument = new Document("filename", "test.txt")
        //        .append("contentType", "text/plain")
        //        .append("fileData", new byte[]{1, 2, 3})
        //        .append("uploadDate", new Date())

        //collection.insertOne(testDocument)
    }

    def cleanup() {

        // Close the MongoDB client connection
        if (mongoClient != null) {
            mongoClient.close()
        }
    }

    /*
    void "test upload method"() {
        given:
        def fileData = new byte[]{4, 5, 6}
        def fileName = "newTestFile.txt"
        def contentType = "text/plain"

        when:
        def result = service.upload(fileData, fileName, contentType)


        then:
        result != null
        //collection.find(new Document("filename", fileName)).first() != null
    }
    */

    void "test download method"() {
        given:
        String id = "66ccebd8fa37a644f14191bd"

        when:
        File downloadedFile = service.download(id)

        then:
        downloadedFile != null
        downloadedFile.bytes == new byte[]{1, 2, 3}
    }

}
