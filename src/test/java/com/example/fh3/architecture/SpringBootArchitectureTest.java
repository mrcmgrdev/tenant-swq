package com.example.fh3.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noFields;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@AnalyzeClasses(packages = "com.example.fh3", importOptions = ImportOption.DoNotIncludeTests.class)
class SpringBootArchitectureTest {

  // --- Package structure rules ---

  @ArchTest
  static final ArchRule controllersShouldResideInControllerPackage =
      classes()
          .that()
          .areAnnotatedWith(Controller.class)
          .or()
          .areAnnotatedWith(RestController.class)
          .should()
          .resideInAPackage("..controller..")
          .as("Controllers should reside in a 'controller' package");

  @ArchTest
  static final ArchRule servicesShouldResideInServicePackage =
      classes()
          .that()
          .areAnnotatedWith(Service.class)
          .should()
          .resideInAPackage("..service..")
          .as("Services should reside in a 'service' package");

  @ArchTest
  static final ArchRule repositoriesShouldResideInRepositoryPackage =
      classes()
          .that()
          .areAnnotatedWith(Repository.class)
          .should()
          .resideInAPackage("..repository..")
          .as("Repositories should reside in a 'repository' package");

  @ArchTest
  static final ArchRule entitiesShouldResideInEntityPackage =
      classes()
          .that()
          .areAnnotatedWith(jakarta.persistence.Entity.class)
          .should()
          .resideInAPackage("..entity..")
          .as("JPA entities should reside in an 'entity' package");

  @ArchTest
  static final ArchRule configClassesShouldResideInConfigPackage =
      classes()
          .that()
          .areAnnotatedWith(org.springframework.context.annotation.Configuration.class)
          .should()
          .resideInAPackage("..config..")
          .as("@Configuration classes should reside in a 'config' package");

  // --- Layer dependency rules ---

  @ArchTest
  static final ArchRule controllersShouldNotDependOnRepositories =
      noClasses()
          .that()
          .resideInAPackage("..controller..")
          .should()
          .dependOnClassesThat()
          .resideInAPackage("..repository..")
          .as("Controllers should not directly depend on repositories");

  @ArchTest
  static final ArchRule controllersShouldNotDependOnEntities =
      noClasses()
          .that()
          .resideInAPackage("..controller..")
          .should()
          .dependOnClassesThat()
          .resideInAPackage("..entity..")
          .as("Controllers should not directly use JPA entities — use DTOs instead");

  @ArchTest
  static final ArchRule servicesShouldNotDependOnControllers =
      noClasses()
          .that()
          .resideInAPackage("..service..")
          .should()
          .dependOnClassesThat()
          .resideInAPackage("..controller..")
          .as("Services should not depend on controllers");

  @ArchTest
  static final ArchRule repositoriesShouldNotDependOnServices =
      noClasses()
          .that()
          .resideInAPackage("..repository..")
          .should()
          .dependOnClassesThat()
          .resideInAPackage("..service..")
          .as("Repositories should not depend on services");

  @ArchTest
  static final ArchRule noCircularDependencies =
      slices()
          .matching("com.example.fh3.(*)..")
          .should()
          .beFreeOfCycles()
          .as("There should be no circular dependencies between packages");

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
          .areAnnotatedWith(Repository.class)
          .should()
          .haveSimpleNameEndingWith("Repository")
          .as("Repository classes should be suffixed with 'Repository'");

  // --- Spring Boot best practices ---

  @ArchTest
  static final ArchRule noFieldInjection =
      noFields()
          .should()
          .beAnnotatedWith(Autowired.class)
          .as("Field injection is not allowed — use constructor injection instead");

  @ArchTest
  static final ArchRule controllerClassesShouldBeAnnotatedWithRestController =
      classes()
          .that()
          .resideInAPackage("..controller..")
          .and()
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

  @ArchTest
  static final ArchRule repositoriesShouldBeInterfaces =
      classes()
          .that()
          .resideInAPackage("..repository..")
          .should()
          .beInterfaces()
          .as("Repositories should be interfaces extending Spring Data repositories");
}
