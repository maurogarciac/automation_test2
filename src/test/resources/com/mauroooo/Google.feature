Feature: Fetching links from a Google search

    Fetching links from google

    Scenario Outline: First N links
        Given Google search is loaded
        When I search for "<value>"
        Then There are at least <amount> links that result from it are saved
        Examples:
            | value   | amount |
            | Banana  | 5      |
            | Pera    | 6      |
            | Manzana | 2      |
            | Naranja | 1      |

    Scenario Outline: First lucky link
        Given Google search is loaded
        When I feel lucky about the term "<value>"
        Then There is a resulting link for the term
        Examples:
            | value   |
            | Perro   |
            | Gato    |
            | Manzana |
            | Naranja |