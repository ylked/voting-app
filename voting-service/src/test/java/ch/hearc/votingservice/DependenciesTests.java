package ch.hearc.votingservice;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class DependenciesTests {

    private static final String ROOT_PACKAGE = "ch.hearc.votingservice";
    private static final String JMS_PACKAGE = "ch.hearc.votingservice.jms..";
    private static final String REMOTE_PACKAGE = "ch.hearc.votingservice.remote..";
    private static final String SERVICE_PACKAGE = "ch.hearc.votingservice.service..";
    private static final String API_PACKAGE = "ch.hearc.votingservice.api..";
    private static final String REPOSITORY_PACKAGE = "ch.hearc.votingservice.repository..";
    private static final String CONFIGURATION_PACKAGE = "ch.hearc.votingservice.configuration..";
    private static final String SHARED_PACKAGE = "ch.hearc.votingservice.repository..";


    @Test
    public void layeredPackagesTest(){

        JavaClasses importedClasses = new ClassFileImporter().importPackages(ROOT_PACKAGE);

        //aucune dépendaces directes de la couche api sur repository et jms, aucune dépendnaces sur configuration
        ArchRule apiPackageRules = noClasses().that().resideInAPackage(API_PACKAGE)
                .should().dependOnClassesThat().resideInAnyPackage(REPOSITORY_PACKAGE,JMS_PACKAGE,CONFIGURATION_PACKAGE);

        //aucune dépendaces de la couche service sur api et configuration
        ArchRule servicePackageRules = noClasses().that().resideInAPackage(SERVICE_PACKAGE)
                .should().dependOnClassesThat().resideInAnyPackage(API_PACKAGE,CONFIGURATION_PACKAGE);

        //aucune dépendances de la couche repository sur tout
        ArchRule repositoryPackageRules = noClasses().that().resideInAPackage(REPOSITORY_PACKAGE)
                .should().dependOnClassesThat().resideInAnyPackage(API_PACKAGE,SERVICE_PACKAGE,JMS_PACKAGE,CONFIGURATION_PACKAGE);

        //aucune dépendances de la couche jms sur tout
        ArchRule jmsPackageRules = noClasses().that().resideInAPackage(JMS_PACKAGE)
                .should().dependOnClassesThat().resideInAnyPackage(API_PACKAGE,SERVICE_PACKAGE,REPOSITORY_PACKAGE,CONFIGURATION_PACKAGE);

        //aucune dépendances de la couche remote sur tout
        ArchRule remotePackageRules = noClasses().that().resideInAPackage(REMOTE_PACKAGE)
                .should().dependOnClassesThat().resideInAnyPackage(API_PACKAGE,SERVICE_PACKAGE,REPOSITORY_PACKAGE,CONFIGURATION_PACKAGE);

        //aucune dépendnace de la couche shared sur tout sauf repository, dataseeder
        ArchRule sharedPackageRules = noClasses().that().resideInAPackage(SHARED_PACKAGE)
                .should().dependOnClassesThat().resideInAnyPackage(API_PACKAGE,SERVICE_PACKAGE,JMS_PACKAGE,CONFIGURATION_PACKAGE);

        //aucune dépendance de la couche configuration
        ArchRule configurationPackageRules = noClasses().that().resideInAPackage(CONFIGURATION_PACKAGE)
                .should().dependOnClassesThat().resideInAnyPackage(API_PACKAGE,SERVICE_PACKAGE,REPOSITORY_PACKAGE,JMS_PACKAGE,SHARED_PACKAGE);

        apiPackageRules.check(importedClasses);
        servicePackageRules.check(importedClasses);
        repositoryPackageRules.check(importedClasses);
        jmsPackageRules.check(importedClasses);
        sharedPackageRules.check(importedClasses);
        remotePackageRules.check(importedClasses);
        configurationPackageRules.check(importedClasses);

    }


    @Test
    public void jmsAndRemotePackagesTest(){

        JavaClasses importedClasses = new ClassFileImporter().importPackages(ROOT_PACKAGE);

        ArchRule jmsPackageRules = noClasses().that().resideInAPackage(JMS_PACKAGE)
                .should().dependOnClassesThat().resideInAnyPackage(
                        REPOSITORY_PACKAGE,
                        SERVICE_PACKAGE,
                        API_PACKAGE);

        ArchRule remotePackageRules = noClasses().that().resideInAPackage(REMOTE_PACKAGE)
                .should().dependOnClassesThat().resideInAnyPackage(
                        REPOSITORY_PACKAGE,
                        SERVICE_PACKAGE,
                        API_PACKAGE);

        remotePackageRules.check(importedClasses);

        jmsPackageRules.check(importedClasses);

    }
}
