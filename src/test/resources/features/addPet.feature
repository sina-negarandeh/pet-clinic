@add_pet
Feature: Add Pet Feature

  Background: Sample General Preconditions Explanation
    Given The pet type "Dog" exists

  Scenario: Add Pet For Owner that already exists
    Given There is an owner called "Tony Stark" who wants a pet
    When He adds a pet for himself by calling save method from pet service
    Then The pet is added successfully
