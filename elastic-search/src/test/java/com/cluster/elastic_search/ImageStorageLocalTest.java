package com.cluster.elastic_search;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import static org.assertj.core.api.Assertions.assertThat;
import com.cluster.elastic_search.Service.ImageStorage;
import com.cluster.elastic_search.Service.ImageStorageLocalImpl;

public class ImageStorageLocalTest {
    
    private ImageStorage storage;

    @BeforeEach
    void setup(){
        this.storage = new ImageStorageLocalImpl();
    }

    @Test
    void guardarYRecuperar(){
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "data".getBytes());
        String url = storage.guardarImagen(file, "test-uuid.jpg");
        assertThat(url).contains("test-uuid.jpg");
    }
}
