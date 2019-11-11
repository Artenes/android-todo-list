# Todroid

Android app to create, mark as done and delete tasks.

Made this app to practice some android development using kotlin.

# What's the catch

In this project I've imposed two restrictions: 
- decided to not use an SQLite database just to see how can I store data in the device in a simples manner;
- implement the observer pattern to send data from the repository to the ui without using LiveData.

I've ended up creating a parser that would serialize/deserialize the tasks list to string to then store in SharedPreferences and create some classes to replicate some of the LiveData API.

# What I've learned

