package com.ilionx.poc;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A dummy rest controller to expose a few (test) endpoints.
 */
@RestController
public class DummyController {

  /**
   * Dummy method.
   */
  @GetMapping("/dummy")
  public ResponseEntity<String> dummy() {
    return ResponseEntity.ok("foo bar");
  }


}
