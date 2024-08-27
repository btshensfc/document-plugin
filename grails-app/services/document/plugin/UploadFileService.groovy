package document.plugin

import grails.gorm.transactions.Transactional
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.*
import com.mongodb.client.FindIterable
import org.bson.Document
import org.bson.types.Binary
import org.bson.types.ObjectId
import java.io.File

import com.mongodb.BasicDBObject
import org.bson.types.ObjectId


@Transactional
class UploadFileService {

    def upload(def fileData, String fileName, String contentType) {
        println("reached uploadfileservicve")
        MongoClient mongoClient = null 
        try {
                mongoClient = MongoClients.create("mongodb://localhost:27017/")
                MongoDatabase database = mongoClient.getDatabase("teachershopdocs")

                // Specify the collection where files will be stored
                MongoCollection<Document> collection = database.getCollection("files")

                if (fileData) {
                        Document doc = new Document()
                        doc.append("filename", fileName)
                        doc.append("contentType", contentType)  // Adjust as needed
                        doc.append("fileData", fileData)
                        doc.append("uploadDate", new Date())

                        collection.insertOne(doc)
                        println("File uploaded successfully to MongoDB with filename: ")
                        return doc._id.toString()

                } else {
                        println("File not found at path:")
                }

        } catch (Exception e) {
                println("Error uploading file to MongoDB: ${e.message}")
        } finally {
                if (mongoClient != null) {
                        mongoClient.close()
                }
        }
    }

    def File download(String id){
        MongoClient mongoClient = null 
        ObjectId objectid = new ObjectId("66cd0bbe4bd2530aba517bdb")
        try {
                mongoClient = MongoClients.create("mongodb://localhost:27017/")
                MongoDatabase database = mongoClient.getDatabase("teachershopdocs") //change this to whichever file server we use
                MongoCollection<Document> collection = database.getCollection("files")

                BasicDBObject query = new BasicDBObject("_id", objectid) //this works 
                Document firstEntry = collection.find(query).first()
                if(firstEntry) {
                    String filename = firstEntry.getString("filename")
                    println("Printing filename at UploadFileService: "+filename)
                    Binary fileData = firstEntry.get("fileData", Binary.class)

                    File tempFile = File.createTempFile("mongoFile", filename)
                    tempFile.bytes = fileData.getData() // Convert Binary to byte[] and write to file

                    return tempFile
                } else {
                    return null 
                }
        } finally { 
            if (mongoClient != null) {
                mongoClient.close()
            }
        }
    }
}
