package com.example.fh3.tenant;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tenants")
@CrossOrigin(origins = "http://localhost:5173")
public class TenantController {

  private final TenantService tenantService;

  public TenantController(TenantService tenantService) {
    this.tenantService = tenantService;
  }

  @PostMapping(version = "1")
  public ResponseEntity<TenantResponse> createV1(@RequestBody TenantRequest request) {
    Tenant tenant = tenantService.create(request.name());
    return ResponseEntity.ok(new TenantResponse(tenant.getId(), tenant.getName()));
  }

  public record TenantRequest(String name) {}

  public record TenantResponse(Long id, String name) {}
}
