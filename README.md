# Zappos Challenge
Public source code for challenge

The challenge is to build an app that takes input from the user as a search query and returns the first item (there could be more than one) and display it as a product page. It does not need to look like Zappos' app, but shown how I would build this from scratch using my own design. The app should have a floating action button on the screen that does some sort of animation to indicate that something has been added to a cart. I will need to use databinding for the product page I create.

Some details within the application (Check Out Button, Product Details) are not 'hooked up' since they are more representational than functional, for the purpose of visualization.

To use this application, run the app, and click on the hourglass icon on the ToolBar. Search for any clothing or items, and you will be provided with a list of products. Click on each product for details, and hit the shopping cart FAB to place the product into your cart.
To delete items in the shopping cart, press and longclick the desired item, and hit 'ok' in the pop-up.

NOTE ABOUT APK:
Signed APK can be found in the apk folder. There are two:
build.apk is just app-release.apk renamed, however I left in both just in case errors occur because of the renaming for any reasons.

# Conclusion & Thoughts:
Overall, this was an extremely fun challenge to test myself and see how well I can perform with the knowledge I currently have, and new tools which I have learned from doing this. This project has helped me better understand my strengths (Workflow, Implementation, General Comprehension), and my weaknesses/not-so-good-at-yets (Design, Detail-Oriented Comprehension). After some review (and of course I can add to this after the challenge), there are several things I wish I had implemented.
1.) (Most Important Wish) With the product page, I wish I had implemented a 'color option' for the product, as with each query, there was bound to be products with the same productID but with different thumbnail images and colorIDs. It would have been nice to implement an image viewpager or some spinner to show the different options of color available for each product seen.
2.) Design is something that will be learned with more experience and input from coworkers and friends. Animations and the implementation of transitions were lacking, but on the bright side the usability with animations dedicated to user input were fair...enough...for now.

So 10/10 Experience, would do it again.


# Requirements:
- [x] Databinding for the product page ( https://developer.android.com/topic/libraries/data-binding/index.html )
- [x] Animation when floating action button clicked ( https://developer.android.com/training/animation/index.html )

Bonus points for:
- [x] Following Material Design guidelines ( https://material.io/guidelines/ )
- [x] Well handled configuration changes ( https://developer.android.com/guide/topics/resources/runtime-changes.html )
- [x] Using retrofit to handle your REST requests ( https://square.github.io/retrofit/ )

The following REST requests can be used to get back search results: https://api.zappos.com/Search?term=&key=b743e26728e16b81da139182bb2094357c31d331

Example:
https://api.zappos.com/Search?term=nike&key=b743e26728e16b81da139182bb2094357c31d331
