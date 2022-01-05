@add_new_pet
Feature: Add New Pet Feature

  Scenario: Add New Pet For Owner that already exists
    Given Owner "Peter Parker" already exists
    When He adds a new pet for himself by calling newPet method from pet service
    Then The new pet is added to owner successfully
