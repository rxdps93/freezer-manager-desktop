# freezer-manager-desktop
Home Freezer Inventory Management System to be used by my wife and I. This is the desktop client component (described below).

# Introduction
## Problem to solve
My wife and I have two freezers - one is the typical small freezer integrated into our refrigerator, and the second is a large chest freezer we keep in our basement. We have difficulty managing everything we keep in these freezers: what do we have, which freezer is it in, how long has it been there, how much do we have, when does it expire, etc. It wouldn't be a problem in need of an involved solution if we only kept a small number of items, but this is not the case. We keep a significant amount of food such that both freezers are ready to burst! For this reason, and to have some fun, I decided to develop a system we could use to better manage our inventory.

## Proposed solution
My intent is to develop a system comprised of four major components:
1. Desktop Client: This will run on a small, low power computer mounted to some wall studs next to the freezer in the basement. It will be designed to require only a keyboard and a monitor. It will host the full set of features I plan to develop.
2. Mobile Client: This will be application designed to run on our phones and comes at request of my wife. In order to prevent needing to make a trip to the basement to simply check what is in the freezer, the ideal solution would be the ability to access this system from her phone. This will be optimized for small touchscreens and will thus be graphically different from the desktop client - stylistic continuity is not of importance. The mobile client will, at first, feature a more limited set of features however it can eventually support everything the desktop client does. This will be developed for Android, because that is what my wife and I use, however if any friends or family would like access to this and have the misfortune of using an iPhone I will consider porting it to iOS.
3. Server Application: This is pretty straightforward - it is the backend system for the whole thing. It will handle communications between the clients and the database. This will run on a small low power computer in my house acting as a server.
4. Database: This is also straightforward - all the data will be stored in a MySQL database hosted on the same server as the backend application.

# Desktop Client Information
## Technology Choices
1. Java
   - This is the language we primarily use at work, therefore it is quite fresh in my mind at all times
   - It is easy to get working on virtually any platform
   - The Android client will use Java as well, which will hopefully allow for some direct code sharing
2. Eclipse
   - This is the IDE I have the most experience with, and since Java is a pain without an IDE it was a nobrainer
3. [Lanterna](https://github.com/mabe02/lanterna)
   - As I mentioned above this is intended to be used with only a keyboard so it seemed only natural to use a text based interface
   - I have plenty of experience with ncurses and C so I was very excited to try my hand at doing something similar in Java
   - After plenty of research I determined Lanterna was the best choice

## Planned Features
1. Add item to inventory
   - An item will have a name, quantity + units, date added (autofilled to today, but editable to a past date), expiration date
2. Remove item from inventory
3. Edit certain item information
   - Only add date and expire date will be editable. If a mistake is made on the other fields it will require a remove + add to correct.
   - I might change this policy in the future
4. Add / Edit / Remove user
   - The concept of a user exists soley to allow for transaction logging for accountability
   - This is not important now but will be when we have children in the future
5. Add / Edit / Remove tag
   - The concept of 'tags' is not fully flushed out, but the idea is items can be tagged for searching and filtering
6. Assign / Unassign tag
   - Assign tags to items (expire when the item is removed). Example: "Turkey: tagged with 'for thanksgiving'"
   - Assign persistent tags to item types (automatically added to every item of a certain type) "Turkey: tagged with 'meat'"
7. Inventory "At A Glance"
   - Act as a dashboard for quickly accessing the most important dashboard information
   - View entire inventory alphabetically
   - View items expiring soon (within 90 days) sorted and color coded by expiration date
   - View items grouped by item type, sorted alphabetically, showing the number of items of that type and soonest expiration date
     - Example: Inventory contains "Chicken, 1 pound, added 1/1/2019, expires 5/5/2020" and "Chicken, 200 grams, added 3/1/2019, expires 7/5/2020" would result in an entry of "Chicken, 2, 5/5/2020" because we have 2 separate instances of chicken and the earliest expiration is on 5/5/2020
8. Detailed Inventory View
   - This will allow for a plethora of sorting and filtering options
   - This functionality is still being designed and flushed out

## Tentative Feature Timing
#### Now
- [x] Differentiate between freezers (basement vs kitchen)
- [x] Add tag
- [x] Edit tag
- [x] Remove tag
- [x] Associate items with tags by tag
- [x] Associate items with tags by item
- [x] Show tags in item summary
- [x] Add user
- [x] Edit user
- [x] Remove user
- [x] Sort user table
- [ ] Assign permissions to a user
- [ ] Unassign permissions from a user
#### Near
- [ ] Make permissions actually do something
- [ ] Allow adding tags to items via the add/edit item dialog
- [ ] Introduce concept of 'item type' as an object
- [ ] Differentiate between permanent and instance tags
- [ ] Rework tag assignment/unassignment to account for tag types
- [ ] Filter detailed inventory view by field
- [ ] Filter detailed inventory view by tag
- [ ] Keep logs of all CSV file transactions
#### Far
- [ ] Ensure documentation is up to a reasonable standard
- [ ] Interface with server application / database for inventory items
- [ ] Interface with server application / database for users
- [ ] Interface with server application / database for tags
- [ ] Keep logs of all database transactions
- [ ] Other things I forgot about :)
#### Future
- [ ] Integrate with digital recipe management system (another project I have planned to help my wife out)

## Completed Feature List
This gets updated whenever the "Now" section above is completed. When that happens those items move here, along with a completion (push) date and then everything is re-prioritized. These records are mostly for my own sake/interest. I enjoy looking back on the progression of my personal projects.
####'Now' Priorities Iteration 1, Completed 4/1/19
- [x] Read test data from a randomly generated CSV file: **Completed 3/20/19**
- [x] Read data on application launch; data is not updated **Completed 3/20/19**
- [x] Inventory "at-a-glance" Alphabetical View **Completed 3/20/19**
- [x] Inventory "at-a-glance" Expiring Soon View **Completed 3/20/19**
- [x] Inventory "at-a-glance" Grouped Item Type Count View **Completed 3/20/19**
- [x] Detailed inventory view with sorting, no filtering **Completed 3/24/19**
- [x] Add item and save it to CSV **Completed 3/27/19**
- [x] Edit item **Completed 4/1/19**
- [x] Remove item **Completed 4/1/19**
- [x] Data updates from CSV after add **Completed 3/28/19**
- [x] Data updates from CSV after edit **Completed 4/1/19**
- [x] Data updates from CSV after remove **Completed 4/1/19**