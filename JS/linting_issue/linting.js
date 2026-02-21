// Unused imports and variables
import React from 'react';
import moment from 'moment';
import axios from 'axios';

const unused_var = "this is never used";

// Global variable without declaration
globalVar = "this is bad practice";

// Function with multiple issues
async function fetchUserData(userId) {
    // Inconsistent quotes
    let name = "John";
    let age = 'twenty';

    // Missing error handling in Promise
    const data = await fetch('https://api.example.com/users/' + userId)
    return data.json()
}

// Arrow function with inconsistent spacing and missing parentheses
const calculateTotal = price=> {
    // Magic numbers
    return price * 1.2 + 5.99
}

// Unused function parameters and missing return
function processUser(user, role, permissions) {
    if(user.age>21){
        console.log('User is adult')
    }
}

// Variable reassignment of const
const CONFIG = { api: 'https://api.example.com' };
CONFIG.api = 'https://new-api.example.com';

// Mixing var, let, and const
var oldStyle = 'bad';
let newStyle = 'better';
const bestStyle = 'best';

// Function with too many parameters and no type checking
function updateUserProfile(name, age, email, address, phone, title, company, department, role, salary) {
    // Potential null reference
    return user.profile.update()
}

// Class with various issues
class userManager{
    // Missing constructor
    // Inconsistent method spacing
    getData(){
        return null
    }

    // Empty function
    processData(){}

    // Console log in production code
    async fetchData() {
        console.log('fetching...');
        console.log('debug info');
        console.warn('warning message');
        
        // Direct DOM manipulation
        document.getElementById('root').innerHTML = '<div>unsafe</div>';
    }
}

// Switch case without default
function getStatus(code) {
    switch(code) {
        case 200:
            return 'OK'
        case 404:
            return 'Not Found'
        case 500:
            return 'Server Error'
    }
}

// Nested ternary operator
const message = age > 18 ? role === 'admin' ? 'Admin Access' : 'User Access' : 'No Access';

// Mixed operators without parentheses
const result = 2 + 3 * 4 / 2 - 1;

// Regex in loop
for(let i = 0; i < items.length; i++) {
    const pattern = /test/g;
    if(pattern.test(items[i])) {
        // Do something
    }
}

// Undeclared variable in catch block
try {
    riskyOperation();
} catch(error) {
    errorMessage = 'Something went wrong: ' + error;
}

// Promise without catch
new Promise((resolve, reject) => {
    resolve('data')
}).then(data => console.log(data))

// Comparing with == instead of ===
if(someValue == null) {
    // Do something
}

// No-restricted-globals
function handleScroll(event) {
    scroll = 100;
}

// Accessibility issues
const template = `
    <div onclick="handleClick()">
        <img src="image.jpg" />
    </div>
`;

// Dead code after return
function processData(data) {
    return data.value;
    console.log('This will never execute');
}

// Export at the end without proper organization
export { userManager, fetchUserData, calculateTotal };
