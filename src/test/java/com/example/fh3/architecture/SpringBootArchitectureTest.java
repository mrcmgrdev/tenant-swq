package com.example.fh3.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noFields;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@AnalyzeClasses(packages = "com.example.fh3", importOptions = ImportOption.DoNotIncludeTests.class)
class SpringBootArchitectureTest {

  // --- No circular dependencies between feature packages ---

  @ArchTest
  static final ArchRule noCircularDependencies =
      slices()
          .matching("com.example.fh3.(*)..")
          .should()
          .beFreeOfCycles()
          .as("There should be no circular dependencies between feature packages");

  // --- Naming conventions ---

  @ArchTest
  static final ArchRule controllersShouldBeSuffixedWithController =
      classes()
          .that()
          .areAnnotatedWith(Controller.class)
          .or()
          .areAnnotatedWith(RestController.class)
          .should()
          .haveSimpleNameEndingWith("Controller")
          .as("Controller classes should be suffixed with 'Controller'");

  @ArchTest
  static final ArchRule servicesShouldBeSuffixedWithService =
      classes()
          .that()
          .areAnnotatedWith(Service.class)
          .should()
          .haveSimpleNameEndingWith("Service")
          .as("Service classes should be suffixed with 'Service'");

  @ArchTest
  static final ArchRule repositoriesShouldBeSuffixedWithRepository =
      classes()
          .that()
          .haveSimpleNameEndingWith("Repository")
          .should()
          .beInterfaces()
          .as("Repository classes should be interfaces");

  // --- Spring Boot best practices ---

  @ArchTest
  static final ArchRule noFieldInjection =
      noFields()
          .should()
          .beAnnotatedWith(Autowired.class)
          .as("Field injection is not allowed — use constructor injection instead");

  @ArchTest
  static final ArchRule controllersShouldUseRestController =
      classes()
          .that()
          .haveSimpleNameEndingWith("Controller")
          .should()
          .beAnnotatedWith(RestController.class)
          .as("Controllers should be annotated with @RestController");

  @ArchTest
  static final ArchRule controllerMethodsShouldBePublic =
      methods()
          .that()
          .areDeclaredInClassesThat()
          .areAnnotatedWith(RestController.class)
          .and()
          .areNotPrivate()
          .should()
          .bePublic()
          .as("Controller handler methods should be public");
}
