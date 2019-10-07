@smokeTest
Feature: To checkout a random product from Batteries section

  @Checkout
  Scenario: To checkout a random product from Batteries section
    Given the user selects Power from AllProducts section
    When the user adds the product to the basket
    And clicks on Checkout securely
    Then the product should appear on the checkout page

  @TestThis
  Scenario: To search for a product and checkout
    Given the user searches for Logitech product
    When the user adds the product to the basket
    And clicks on Checkout securely
    Then the product should appear on the checkout page

  @Filters
  Scenario: To filter by Brand
    Given the user selects Connectors from AllProducts section
    When the user selects Brand filters
    Then the filtered results should display
#    And clicks on Checkout securely
#    Then the product should appear on the checkout page

@Test1
  Scenario: To filter by Brand
    Given the user selects Connectors from AllProducts section
    When the user selects filters section
     | filterType | numberOfFilters | subFilter |
#    If the filter type is input - numberOfFilters column needs to have a value of 1 always
#     | Brand      | 1               | 1         |
#
     |   Random   |     2           |   1     |
#      |   None     |     2           |   1     |

  Then the filtered results should display
#  Then I check the results




