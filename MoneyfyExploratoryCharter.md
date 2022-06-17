# Moneyfy Exploratory Testing Charter (Android)
#### Monefy is a personal finance application that makes money management easy. The app is designed to streamline expense tracking and help you save money.
### Areas Covered
* Addition of income and expenses.
* Categorizing the income and expenses.
* Creating and viewing income and expenses on various time periods.
* Creating and viewing income and expenses based on account types.
* Addition and updation of accounts. 
* Transfering the amount between different accounts
* Currency validation in differenct sections of application
* General application settings.
* Synchronizing and databackup of the applications.

### Tester
- [@Likhitha NR](https://github.com/likhithanr)

## Test Scenarios
### Monefy Home screen validation (High)
* Monefy app heading should be displayed on the leftside of the header as “monefy”.
* Header should have menu button in top left corner and should be clickable 	and it should display “All Accounts and time period for transaction display”.
* Search button should be dispalyed on right side of the header.
* Double arrow should be displayed on right side of header ,on clicking it should take us to new transfer window.
* Three veritical doted line should be displayed in top right corner of the header, on	clicking it should display “Categories, Accounts, Currencies, Settings” options.
* Red color circle with – sign should be displayed to add expenses, on clicking it should take us to “New expense” window.
* Green color circle with + sign should be displayed to add income,on clicking 	it should take us to “New income” window.
* Rectangular box should be displayed to show balance in green color on top of add and expense circular button.
* Two menus should be displayed on left and right side of “Balance field” , on 	clicking both  should take us to display list of added expenses and income 	along with balance field.
* Expense categories should be displayed in around the balance component in a circualr fashion with respective icon for the category.
* Clicking on any category should take us to new expense with the selected category being preselected and category name being displayed at the bottom.
* When no income or expense is added the circle around the balance component should be greyed. 
* Default vaule of balance inside the circular component in the centre should be zero. 
* Click on menu which is present just above +/- circle and it should list out all 	the added expenses and incomes , delete all the income and navigate to home 	screen Balance and amount in circular balance component in center should be reset to 0.
* Click on menu on top left corner of the header and it should display 	drop down to list out accounts , Day,Week,Month,Year,All,Interval and 	choose date options.
### Add income validation (High)
* Click on green color circle + button and it should take us to New income window
* New income label should be displayed with back arrow button. Clicking on the button should navigate back to home screen.
* Below header selected date,month and year should be displayed in day of the week,day month(Ex: Thursday, 16 June) format along with calendar button.
* A green text field should be displayed with clear button a and button to show account type.
* A text field should be present to add note with “Note and pen sybmol”.
* A calculator should be displayed with 0-9 digits and +,-,*,/,=,. symbols.
* Click account type button displaying in left side of the green text field box it should display all configured type default options should be “Cash,Payment card”
* User should be able to select account type and appropriate account symbol should be udpated with currency symbol
* Enter some number into text field and click on clear button “<--”, entered data should be cleared
* User should be able to enter any alphanumeric characters into "Note" field. 
* User should not be allowed to click on choose category when amount is empty or negative.
* Click on select date calendar buton and user should be able to select required date on calender and is able to select or cancel the selection.
* Enter some value into add money text field and click on CHOOSE CATEGORY, it should display Deposites, Salary, Saving and along with these +Add should be displayed to add extra category.
* Enter amount and note in add income.Navigate back to home screen without saving. Now select Add income again the previously entered data should not persist.
### Add expense validation (High)
* Click on red color circle buttom - (Expense) and it should take us to “New expense” window.
* New income label should be displayed with back arrow button. Clicking it should take us to home screen.
* Just below header selected date, month and year should be displayed in “day of the week,day year”((Ex: Thursday, 16 June)) format along with calendar button.
* A green color text field should be displayed with clear button and button to show account type.
* A text box should be present to add note with “Note and pen symbmol”.
* A calculator should be displayed with 0-9 digits and +,-,*,/,=,. symbols.
* Clicking account type button displaying in left side of the green text field box it should display all configured type default options should be “Cash,Payment card”
* User should be able to select account type and appropriate account symbol should be udpated with currency symbol.
* Entered data should be cleared on clicking the button with sysmbol “<--”(clear)
* User should be able to enter alphanumeric characters into note field.
* User should be allowed to click on choose category when add money field is empty or negative.
* Click on select date calendar buton and user should be able to select required date with calendar details and ok and cancel button.
* Enter some value into add money(text field) and click on "CHOOSE CATEGORY" should display all the categories that are displayed in home screen.
* Enter amount and note in add income.Navigate back to home screen without saving. Now select Add income again the previously entered data should not persist.

### Accounts (High)
* Click on three dots on right top corner of the main screen. Select "Accounts" menu, it should display two icons to "Transfer" and "Add" account besides text "Add". 
* Add section should list out available accounts like Cash and Payment card.
* Click on + button it should take user to "New account" screen.
* On header of "New account" screen  it should display "<--(Back)", "New account(Title)" and "Add".
* New account screen should have a text box with colo green to add "Name" of the account.
* New account should display "Currency" ,"Intial account balance" and "Initial balance date" and "Included in the toggle" button.
* Updating selection of currency type from the "Currency" field should update currency type (Pro feature)
* Enter amount value with (.) dot it should accept the amount
* Enter amount separated with (,) coma it is not accepting coma(BUG)
* Clicking on "Initial balance date", it should allow user to select past, current future dates from the calender widget.
* Creating a user should not be allowed without selecting a icon and name. 
* Enter valid name ,image and amount also select date then click on ADD it should navigae back to home screen with new account was added message, now click on main menu on left top corner and click on first drop down it should 	display newly added account with currency type and selected image.
* Select that newly added account from the left top corner menu and navigate back to home screen, it should display the amount added in green circle at the center.
* Click on three vertical doted line menu ,navigate to Accounts it should display newly added account with added amount value.
* Click on newly added account and it should take us to Edit account window and click on delete button , account should be deleted with "Account was deleted" message,it should also disappear from home page left side menu.
* Creating duplicate account with same name and currency should not be allowed (BUG)
* There should be Save/cancel button present in Edit account screen (BUG)
* Select existing account it should take us to “Edit account” screen, edit name /amount then click on back arrow button, record should not be updated (BUG)
* Edit the account with "Initial balance date" as future date ex: account 1: 6/18/22, account 2:6/17/22 – should be current date and save the changes, then go to home page menu and select account 1 and click on "Day" amount should not be present in present date it should be seen in next dates slide Ex: 6/18/22.
* Click on double arrow(Transfer) under "Account", it should take us to New transfer screen.
* Delete all the accounts and navigate to home screen , click on green / red 	color circle it should take us to add “New account” window
* Have only one account, Add some money to the account and enable "include in balance", Navigate to home screen in the left top menu, select "All accounts", as there is only one account added amount should be displayed in balance inside big circle at the center of screen (BUG)
* Disable “Included in the balance” for the same account should not 	display amount in home screen “Balance field” (BUG)


### Validate new transfer (Medium)
* Click on double arrow button present at the right top side on the home screen header, it should take user to New Transfer screen.
* Selected date and year should be displayed below New transfer header
* Text box with green color to enter the amount should be displayed with clear button at right side and currency name a left side of the text field.
* Add Note text field should be displayed
* Two dropdown should be displayed for selecting sender and receiver account. Dropdown should display all the accounts configured by user. 
* Between two account drop down fields down arrow should be displayed in direction from sender to receiver account.
* By default drop downs should have “cash and payment card” option if no new types are configured by user.
* Select same accounts from both the drop downs and click in round shapped button present at the bottom it should show “Accounts have to be different”
* Select different account option in drop downs and click on “ADD TRANSFER”, add money text field should be highlighted in red.
* Add some money and click on ADD TRANSFER it should take back us to home screen and with success message.
* Add some negative money and click on ADD TRANSFER it should not allow user to transfer.
* Add some valid amount and select differnt drop down click on ADDTRANSFER then money should be transfered to respective account to ensure that by navigating to main manu in home page All account -->Click on account, amount should be deducted from sender account and the same amount should be received in receiver account.
* Enter some number and click on – and enter another digit (BUG) it is not displaying airthmetic signs.
* Enter some digit and click on any +/- then click on clear button. Enter some other number and click on = (BUG) Clear button is not fully clearing the data.


### Validating right top corner menu with submenus Categories, Currencies and Settings (Medium)
* Clicking on “Categories” should display EXPENSES and INCOME sections. 
* Expenses categories should list out all the available categories option in alphabetical order.
* Clicking on each category should allow you to edit category and delete category.
* Select any category ex: HOME and it should take us to edit category window and click on delete button, it should be succesfully deleted from category list and from the application home screen.	
* Click on + button present at EXPENSES then click on deleted category and save it should be added back (Pro feature)
* Click on Deposits/Salary/Savings it should take us to Edit category.User should be able to edit name and delete the category.
* Double tap on category should collapase category list.

### Validating settings (Low)
##### Validating BALANCE option
* Clicking on settings under right top corner menu button should display "Budget","Carry over" and "Future recurring records" check boxs option should be displayed.
* User should be able to check and uncheck the Budget option
* On selecting Budget check box asome amount should be added by default 
* Selecting Budget checkbox a dialog should be displayed to add money which is greater than 0
* Click on Carry over check box and it should carry over all the previous day amount into present day and there should another value 	added in home screen big circle.
* Delete all the expenses, income and accounts and add some amount in budget on home screen it should display only Budget amount and select each time period option present in home screen menu. Displays wrong amount in each time period. (BUG)

##### Validating GENERAL SETTING 
* Unlock ,dark mode ,passcode protection -->Pro feature
* Update to new currency by selecting any other curreny ex:INR, currency should be updated in all the places.
* Click on language and  change the language selection, changed language should be updated every where.

##### Data synchronization (High)
* Data from the application should be synchronized in Dropbox and google drive (Pro feature)
##### Data backup (High)
* Click on create data backup it should ask for local folder to save the backupfile.
* Click on Restore data it should ask for file to upload and restore the data.
* Create a data backup. Delete the application and download the application. Click on restore data and select the previous back up. Data should be restored to previous state. 
* Clicking on Restore data, it should not accept pdf and text formated file (BUG)

### Bugs
* From right top corner menu, select the "Categories". Select individual categories and delete each one of them. Now from the home screen try adding expense with + button. You are not allowed to add expense or deposit as there are no categories. Add category is Pro feature but it is blocker for gerneral user.
* Add income with + button on home screen. Select "Settings" menu and change the currency to any other Ex: INR. Navigate back to home screen. Amount still displays the same without conversion, expected to change the value based on exchange rate on currency type change.
* Enter some number and click on – and enter another digit, It does not display arithmetic sign 
* Enter some digit and click on any +/- then click on clear button again enter some digit and click on = , full data is not being cleard
* Creating account(duplicate) with same name and currency is allowed
* Edit account screen does not have a save/cancel option 
* Have only one account, Add some money to the account and enable include in balance, Navigate to home screen in the top menu, select all account, as there is only one account added amount is not displayed in balance field
* Navigate to account and select a account to edit. Toggle "Include in the balance" is not behaving properly (Disabling it also displays balance in homescreen)
* Delete all the expenses, income and accounts and add some amount in budget on home screen it should display only Budget amount and select each time period option present in home screen menu. Displays wrong amount in each time period.
* Screen does not rotate even after enabling screen rotation in mobile 
* Select new transfer. Click on amount field. There is no option to comeback to enter the note

### Observations
* Add expense on each category type in the home screen, links(line connecting) from center circle to category type is missing for some of the categories.
* Expense percentage does not display decimal points, hence is not accurate value.
* Adding new expense on home screen by selecting invidual category, rearranges the category around the center circle.

### Time planned for Charter
There are total of 99 test cases with 66 (High) , 24 (Medium), 9(Low) priority testcases. Total duration for a single tester to run the exploratory test cases would be 120 minutes.
* Monefy home screen validation (20 min)
* Add income validation (20 min)
* Add expense validation (20 min)
* Accounts (30 min)
* Validate new transfer (20 min)
* Validate right top corner menu (10 min)
* Validate Settings (20 min) 
### Prioritisation
#### Test cases mention below are prioritised in ascending order
* Monefy home screen validation, because it is the starting screen of the app
* Add income validation ,because it is core operation
* Add expense validation,because it is core operation
* Accounts
* Validate synchronization of data
* Validate new transfer 
* Validate right top corner menu 
* Validate Settings 
### Risk and Mitigation
 * Risks
   * Loosing of data. Since data is not backed up automatically or there is no data synchronization locally , there is good chance of loosing data
   * Home screen is not very user friendly. User might be confused and might stop using the application
 * Mitigation
   * Storing data in timely manner or allowing generic user to synchronize data locally should be allowed. 
   * Better UI/UX design could help user to understand app faster and use it extensively