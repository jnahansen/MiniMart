MiniMart Example
================
Walmart Android app assignment:
Create a new application with WalmartLabs Android Test API integrated. We’ll be looking at your coding style,
use of data structures, collections, and overall Android SDK knowledge. It’s up to you to impress us with this assignment.
The list of a products can be a simple or as “fancy” as you’d like it to be. Include product image for each product. Try to have some fun.

The application will have two screens.
    Screen 1: - First screen should contain a List of all the products returned by the Service call. - The list should support Lazy Loading.
                When scrolled to the bottom of the list, start lazy loading next page of products and append it to the list. - When a product
                is clicked, it should go to the second screen.
    Screen 2: - Second screen should display details of the product.

BONUS :
- Handling orientation changes efficiently will be a plus.
- BONUS : Ability to swipe to view next/previous item ( Eg: Gmail App)
- BONUS: Universal app that works on both phones and tablets.

Prerequisites
-------------
- Android API Level v9 or higher
- Android Build Tools v22 or higher
- Google Repository v26 or higher

How to Build
------------

This app uses the Gradle build system. To build the project, use the
"gradlew build" command.

Implementation Notes
--------------------
        - MainActivThis sample demonstrates ...ity starts a ProductListFragment containing a RecyclerView.
        - ProductListFragment calls an AsyncTask when user scroll touches bottom of list.
          Only one fetch task is allowed to run at a time. AsyncTask calls Retrofit to get
          more Products from the API. Product instances are stored in Products class.
        - Using Retrofit for network access and GSON for json conversion
        - Products (singleton) class is like a "Model" that contains a list of Product instances fetched from API
          The Model is separate from Activity/Fragment lifecycle and even adapter so that the product data persists
          separate from GUI/orientation changes.
        - Using Picasso for loading/cache-ing images.
        - Product detail is displayed using a ProductDetailFragment started from a ProductDetailActivity
          The list and detail fragments will make it easier to support side-by-side tablet layouts  (tbd).
        - ProductDetailFragment supports supports swipe left/right to move to adjacent products in list.
          ProductDetailActivity can animate to left or right so the navigation is visual.
        - BACK button from product detail page goes back to product list. BACK button tap while in product
          list finishes the activity and purges the data (to enable clean restart).


License
-------
Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.
