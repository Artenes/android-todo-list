# Sample To do app in Android

<a href='https://play.google.com/store/apps/details?id=bok.artenes.todoapp&pcampaignid=pcampaignidMKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png' width='250px'/></a>

![to do app running](images/todo.gif)

Android app to create, mark as done and delete tasks.

Made this app to practice some android development using kotlin.

# Video demo

[Watch demo on youtube](https://youtu.be/8MEAoHnURMo)

# What's the catch

In this project I've imposed two restrictions: 
- decided to not use an SQLite database just to see how can I store data in the device in a simples manner;
- implement the observer pattern to send data from the repository to the ui without using LiveData.

I've ended up creating a parser that would serialize/deserialize the tasks list to string to then store in SharedPreferences and create some classes to replicate some of the LiveData API.
