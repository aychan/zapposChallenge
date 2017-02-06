# Zappos Challenge
Public source code for challenge

The challenge is to build an app that takes input from the user as a search query and returns the first item (there could be more than one) and display it as a product page. It does not need to look like Zappos' app, but shown how I would build this from scratch using my own design. The app should have a floating action button on the screen that does some sort of animation to indicate that something has been added to a cart. I will need to use databinding for the product page I create.

# Requirements:
- [ ] Databinding for the product page ( https://developer.android.com/topic/libraries/data-binding/index.html )
- [ ] Animation when floating action button clicked ( https://developer.android.com/training/animation/index.html )

Bonus points for:
- [ ] Following Material Design guidelines ( https://material.io/guidelines/ )
- [ ] Well handled configuration changes ( https://developer.android.com/guide/topics/resources/runtime-changes.html )
- [x] Using retrofit to handle your REST requests ( https://square.github.io/retrofit/ )

The following REST requests can be used to get back search results: https://api.zappos.com/Search?term=&key=b743e26728e16b81da139182bb2094357c31d331

Example:
https://api.zappos.com/Search?term=nike&key=b743e26728e16b81da139182bb2094357c31d331
