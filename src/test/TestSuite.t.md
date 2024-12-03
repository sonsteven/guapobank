# GuapoBankTestSuite

## Controllers Tests
### AdminControllerTest
* TestAdminHome
  * Tags: controller, admin, home
  * Ensure that AdminHome view is returned correctly.
* TestViewUserAccounts
  * Tags: controller, admin, user, account
  * Check that user accounts are displayed correctly.
* TestFreezeAccount
  * Tags: controller, admin, account
  * Validate freezing account functionality.
* TestUnfreezeAccount
  * Tags: controller, admin, account
  * Validate unfreezing account functionality.

### HomeControllerTest
* test_index_redirectToLogin
  * Tags: controller. home, redirect, login
  * Verify that when no user is logged in, accessing the index redirects to the login page.
* test_index_redirectToAdminHome
  * Tags: controller, home, redirect, admin
  * Verify that when an admin user is logged in, accessing the index redirects to the admin home page.
* test_index_redirectToUserHome
  * Tags: controller, home, user, redirect
  * Verify that when a standard user is logged in, accessing the index redirects to the user home page.
* test_login_notLoggedIn
  * Tags: controller, login, view
  * Verify that the login page is displayed when no user is logged in.
* test_login_redirectToAdminHome
  * Tags: controller, login, admin, redirect
  * Verify that logged-in admin users are redirected to the admin home page upon visiting the login page.
* test_login_redirectToUserHome
  * Tags: controller, login, user, redirect
  * Verify that logged-in standard users are redirected to the user home page upon visiting the login page.
* test_authenticate_invalidCredentials
  * Tags: controller, login, authentication, error
  * Verify that an error message is displayed when authentication fails due to invalid credentials.
* test_authenticate_adminLogin
  * Tags: controller, login, authentication, admin, redirect
  * Verify that admins with valid credentials are redirected to the admin home page after successful authentication.
* test_authenticate_userLogin
  * Tags: controller, login, authentication, user, redirect
  * Verify that users with valid credentials are redirected to the user home page after successful authentication
* test_home_redirectToLoginAdmin
  * Tags: controller, home, admin, redirect
  * Verify that an admin user attempting to access the user home page is redirected to the login page.
* test_home_displayUserHomeWithData
  * Tags: controller, home, user, data
  * Verify that the user home page displays the user's details, accounts, and transaction history properly.
* test_logout_redirectToLogin
  * Tags: controller, logout, redirect
  * Verify that the user is redirected to the login page upon logging out.

### NotificationControllerTest
* test_viewNotifications_loggedIn
  * Tags: controller, notification, view, loggedIn
  * Validate that the notifications are displayed correctly for a logged-in user.
* test_viewNotifications_notLoggedIn
  * Tags: controller, notification, redirect, login
  * Validate behavior for redirecting to the login page when viewing notifications not logged in.
* test_markNotificationAsRead_success
  * Tags: controller, notification, update, read
  * Verify marking notification as read works correctly for logged-in user.
* test_markNotificationAsRead_notLoggedIn
  * Tags: controller, notifications, redirect, login
  * Verify that attempting to mark a notification as read while not logged in redirects to the login page.
* test_markNotificationAsRead_noNotification
  * Tags: controller, notifications, error
  * Verify that attempting to mark a non-existent notification as read does not update any records and redirects to the notifications page.
* test_markNotificationAsRead_wrongUserNotification
  * Tags: controller, notifications, error
  * Verify that attempting to mark a notification as read by a user who does not own it does not update the notification and redirects them to the notifications page

### TransactionControllerTest
* TestShowTransactionForm
  * Tags: controller, transaction
  * Validate transaction form appearing on Spring model.
* TestCreateTransactionSuccess
  * Tags: controller, transaction
  * Validate a successful transaction creation.
* TestCreateTransactionInvalidRecipient
  * Tags: controller, transaction
  * Validate catching an invalid transaction as a result of a nonexistent recipient.
* TestCreateTransactionInvalidSender
  * Tags: controller, transaction
  * Validate catching an invalid transaction as a result of a nonexistent sender.
* TestCreateTransactionOverBalance
  * Tags: controller, transaction
  * Validate catching an invalid transaction as a result of the amount going over the account's balance.
* TestCreateTransactionFrozenAccount
  * Tags: controller, transaction
  * Validate catching an invalid transaction as a result of a frozen account.
* TestCreateTransactionInvalidTransaction
  * Tags: controller, transaction
  * Validate catching an invalid transaction as a result of an internal creation error.


## Entities Tests
### AccountTest
* testParameterizedConstructor
  * Tags: entities, account, constructor
  * Validate parameterized constructor functionality.
* testSetAndGetId
  * Tags: entities, account
  * Validate ID getter and setter.
* testSetAndGetAccountType
  * Tags: entities, account
  * Validate account type getter and setter.
* TestSetAndGetBalance
  * Tags: entities, account
  * Validate account balance getter and setter.
* TestSetAndGetFrozen
  * Tags: entities, account
  * Validate account frozen status getter and setter.
* TestSetAndGetInterestRate
  * Tags: entities, account
  * Validate account interest rate getter and setter.
* TestSetAndGetMinimumBalance
  * Tags: entities, account
  * Validate account minimum balance getter and setter.
* TestSetAndGetOverdraftOptIn
  * Tags: entities, account
  * Validate account overdraft opt in status getter and setter.
* TestSetAndGetOverdraftFee
  * Tags: entities, account
  * Validate account overdraft fee getter and setter.
* testDailyTransactionLimitForUserTypes
  * Tags: entities, account
  * Validate daily transaction limit for user types.

### NotificationTest
* test_notificationConstructor
  * Tags: entity, notification, constructor
  * Validate that the notification entity's constructor initializes correctly.
* test_notificationParameters
  * Tags: entity, notification, parameters
  * Validate that the notification entity's parameters are correctly assigned.
* test_gettersAndSetters
  * Tags: entity, notification
  * Validate the notification getters and setters.
* test_getId
  * Tags: entity, notification
  * Validate that the getId method returns the unique and correct ID for a notification.

### TransactionTest
* TestGetSetId
  * Tags: entities, transaction
  * Validate getting and setting of transaction ID.
* TestGetSetAmount
  * Tags: entities, transaction
  * Validate getting and setting of transaction amount.
* TestGetTransactionDate
  * Tags: entities, transaction
  * Validate getting of transaction date.
* TestGetSetSenderAccount
  * Tags: entities, transaction
  * Validate getting and setting of transaction sender account.
* TestGetSetRecipientAccount
  * Tags: entities, transaction
  * Validate getting and setting of transaction recipient account.

### UserTest
* TestGetSetId
  * Tags: entities, user
  * Validate getting and setting of user ID.
* TestAddAccount
  * Tags: entities, user
  * Validate adding an account to a user.
* TestGetSetAccounts
  * Tags: entities, user
  * Validate getting and setting of a list of accounts.
* TestGetSetPassword
  * Tags: entities, user
  * Validate getting and setting of user password.
* TestGetSetUsername
  * Tags: entities, user
  * Validate getting and setting of username.
* TestGetSetFirstName
  * Tags: entities, user
  * Validate getting and setting of user first name.
* TestGetSetLastName
  * Tags: entities, user
  * Validate getting and setting of user last name.
* TestGetSetRole
  * Tags: entities, user
  * Validate getting and setting of user role.
* TestIsSetVip
  * Tags: entities, user
  * Validate setting and checking of user VIP status.
* TestIsSetMilitary
  * Tags: entities, user
  * Validate setting and checking of user military status.
* TestIsSetCorporate
  * Tags: entities, user
  * Validate setting and checking of user corporate status.


## Services Tests
### AccountServiceImplTest
* TestFindAll
  * Tags: services, accounts
  * Validate finding all accounts.
* TestSaveAccount
  * Tags: services, accounts
  * Validate saving an account.
* TestFindByUser
  * Tags: services, accounts
  * Validate finding accounts by user.
* TestFindById
  * Tags: services, accounts
  * Validate finding accounts by ID.

### InterestServiceImplTest
* TestApplyInterest
  * Tags: services, interest
  * Validate applying interest to an account.
* TestApplyInterestToAll
  * Tags: services, interest
  * Validate applying interest to all accounts with interest rate.
* TestScheduleInterest
  * Tags: services, interest
  * Validate scheduling interest application.

### NotificationServiceImplTest
* test_sendNotification_success
  * Tags: service, notification
  * Verify the sendNotification method successfully creates and saves a notification.
* test_sendNotification_saveException
  * Tags: service, notification, error
  * Verify that the sendNotification method handles exceptions properly when saving fails.
* test_getNotificationsForUser_success
  * Tags: service, notification
  * Validate that the method correctly retrieves the notifications associated for the user.
* test_getNotificationsForUser_emptyList
  * Tags: service, notification
  * Validate that the method correctly handles the case where the user has no notifications.

### TransactionServiceImplTest
* TestFindAll
  * Tags: services, transaction
  * Validate finding all transactions in the repository.
* TestSave
  * Tags: services, transaction
  * Validate saving a single transaction in the repository.
* TestFindBySenderAccount
  * Tags: services, transaction, account
  * Validate finding a list of transactions sent from a specific account.
* TestFindByRecipientAccount
  * Tags: services, transaction, account
  * Validate finding a list of transactions that a specific account has received.
* TestCalculateTransferFeeAmounts
  * Tags: services, transaction
  * Validate calculation of the additional transfer fee of a transaction based on amount transferred.
* TestCalculateTransferFeeCorporate
  * Tags: services, transaction
  * Validate calculation of the additional transfer fee of a transaction based on corporate status.
* TestCalculateTransferFeeMilitaryAndVip
  * Tags: services, transaction
  * Validate calculation of the additional transfer fee of a transaction based on both military and VIP status.
* TestCalculateTransferFeeMilitary
  * Tags: services, transaction
  * Validate calculation of the additional transfer fee of a transaction based on military status.
* TestCalculateTransferFeeVip
  * Tags: services, transaction
  * Validate calculation of the additional transfer fee of a transaction based on VIP status.
* TestIsOverDailyTransactionLimit
  * Tags: services, transaction
  * Validate that the daily transaction limit of a user properly allows/disallows a new transaction.
* TestCreateTransaction
  * Tags: services, transaction
  * Validate the creation of a new transaction.

### UserServiceImplTest
* TestSuccessfulAuthenticate
  * Tags: service, user
  * Validate a successful user authentication.
* TestAuthenticateWrongPassword
  * Tags: service, user
  * Validate catching an invalid authentication using an incorrect password.
* TestAuthenticateInvalidUser
  * Tags: service, user
  * Validate catching an invalid authentication using an incorrect username.
* TestFindAll
  * Tags: service, user
  * Validate finding all users stored in the repository.
* TestSave
  * Tags: service, user
  * Validate saving a user in the repository.
* TestFindByIdSuccess
  * Tags: service, user
  * Validate a successful return of a user search by ID.


## SeleniumTesting
* test_page_load
  * Tags: selenium, page
  * Validate page loading functionality.
* test_login_user
  * Tags: selenium, page
  * Validate admin login functionality.
* test_login_admin
  * Tags: selenium, page
  * Validate logging in as an admin.
* test_login_invalid_password
  * Tags: selenium, page
  * Validate behavior for invalid password login.
* test_view_notifications
  * Tags: selenium, page
  * Validate viewing notifications inbox.
* test_user_logout
  * Tags: selenium, logout, user
  * Validate user logout functionality.
* test_admin_logout
  * Tags: selenium, logout, admin
  * Validate admin logout functionality.
* test_view_accounts
  * Tags: selenium, accounts
  * Validate viewing a users accounts.
* test_freeze_and_unfreeze_account
  * Tags: selenium, accounts, freeze, unfreeze
  * Validate freezing and unfreezing an account.
* test_create_transaction
  * Tags: selenium, account, transaction
  * Validate creating a transaction successfully.
* test_invalid_transaction_missing_account
  * Tags: selenium, account, transaction
  * Validate behavior for creating a transaction when the sender or recipient account is missing.
* test_invalid_transaction_insufficient_balance
  * Tags: selenium, account, transaction
  * Validate behavior for creating a transaction for an account with insufficient balance and no overdraft enabled.
* test_invalid_transaction_exceeds_minimum_balance
  * Tags: selenium, account, transaction
  * Validate behavior for creating a transaction for an account with amount exceeding the account minimum balance.
* test_invalid_transaction_frozen_account
  * Tags: selenium, account, frozen, transaction
  * Validate behavior for creating a transaction for a frozen account.
* test_invalid_transaction_exceed_daily_limit
  * Tags: selenium, account, frozen, transaction
  * Validate behavior for creating a transaction when the account has reached its daily limit.