package document.plugin

import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import spock.lang.Specification

import grails.testing.mixin.integration.Integration
import org.springframework.beans.factory.annotation.Autowired

@Integration
@Rollback
class UploadFileServiceSpec extends Specification {

    @Autowired
    UploadFileService uploadFileService

//    def setup() {
//    }

//    def cleanup() {
//    }

    void "test upload and download data"() {
        given:
        def fileData = new byte[] {1, 2, 3}
        def fileName = "test.txt"
        def cointentType = "text/plain"

        when: 
        def fileId = uploadFileService.upload(fileData, fileName, contentType)

        then:
        fileId != null // Ensure the file was uploaded successfully

        when: 
        def downloadedFile = uploadFileService.download(fileId)

        then: 
        downloadedFile != null
        downloadedFile.bytes == filedata

    }
}
