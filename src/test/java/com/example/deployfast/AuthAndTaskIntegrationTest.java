package com.example.deployfast;

import com.example.deployfast.dto.AuthRequest;
import com.example.deployfast.model.Task;
import com.example.deployfast.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthAndTaskIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @Test
    void testAuthAndTaskFlow() throws Exception {
        // 1. Register
        AuthRequest registerRequest = new AuthRequest();
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");

        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated());

        // 2. Login
        MvcResult loginResult = mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String response = loginResult.getResponse().getContentAsString();
        String token = objectMapper.readTree(response).get("accessToken").asText();

        // 3. Create Task
        Task task = Task.builder()
                .title("Test Task")
                .description("Test Description")
                .status(Task.TaskStatus.TODO)
                .priority(Task.TaskPriority.MEDIUM)
                .build();

        mockMvc.perform(post("/api/v1/tasks")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Task"));

        // 4. Get Tasks
        mockMvc.perform(get("/api/v1/tasks")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Test Task"));
    }
}
