package com.example.fh3.tenant;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TenantController.class)
class TenantControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private TenantService tenantService;

  @Test
  void postTenant_shouldReturnCreatedTenant() throws Exception {
    Tenant tenant = new Tenant("My Company");
    tenant.setId(1L);
    when(tenantService.create("My Company")).thenReturn(tenant);

    mockMvc
        .perform(
            post("/api/v1/tenants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"My Company\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("My Company"));
  }
}
