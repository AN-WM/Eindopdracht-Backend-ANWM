package nl.novi.EindopdrachtBackend.dtos;

import jakarta.validation.constraints.NotBlank;

public class DocumentDto {
    private Long id;
    @NotBlank
    private String docName;
    private byte[] docFile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public byte[] getDocFile() {
        return docFile;
    }

    public void setDocFile(byte[] docFile) {
        this.docFile = docFile;
    }
}
