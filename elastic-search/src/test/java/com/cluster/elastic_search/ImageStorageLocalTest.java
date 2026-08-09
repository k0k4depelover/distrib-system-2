package com.cluster.elastic_search;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import com.cluster.elastic_search.Service.ImageStorage;

@SpringBootTest
public class ImageStorageLocalTest {
    
    @Autowired
    ImageStorage storage;

    @Test
    void guardarYRecuperar(){
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "data".getBytes());
        String url = storage.guardarImagen(file, "test-uuid.jpg")
    }
}
