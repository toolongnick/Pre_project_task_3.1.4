//GET ALL USERS
const URL = '/api/people/'
let tableBody = document.querySelector('.allUsers')

let userRoles = (user) => {
    let roleInnerHTML = ''
    user.roles.forEach(role => {
        roleInnerHTML += (role.role === 'ROLE_USER' ? 'USER ' : 'ADMIN ')
    })
    return `<td>` + `<a>${roleInnerHTML}</a>` + `</td>`
}


let insertedTRFunc = (user) => {
    return `<tr id="${user.id}">
                           <td id="allUsersID" >${user.id}</td>
                           <td id="allUsersFN">${user.firstName}</td>
                           <td id="allUsersLN">${user.surname}</td>
                           <td id="allUsersAge">${user.age}</td>
                           <td id="allUsersPassword" hidden>${user.password}</td>
                           <td id="allUsersEmail">${user.email}</td>`
        +
        userRoles(user)
        +
        `<td><button type="button" class="editBtn btn btn-primary">Edit</button></td>
                           <td><button type="button" class="deleteBtn btn btn-danger">Delete</button></td>
                    </tr>`
}

let basicTableOutput = ''

function fetchAllUsers() {
    fetch(URL)
        .then(res => res.json())
        .then(data => {
            data.forEach(user => {
                basicTableOutput += insertedTRFunc(user)
            })
            tableBody.innerHTML = basicTableOutput
        })
}

const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if (e.target.closest(selector)) {
            handler(e)
        }
    })
}

function selectedOptions() {
    const selects = document.querySelector('.editModalSelect')
    let editFinalRoles = []
    const selectedValues = [].filter.call(selects.options, option => option.selected)
        .map(option => option.text)
    selectedValues.forEach((chosen) => {
        chosen === 'USER' ? editFinalRoles.push({id: 1, role: "ROLE_USER"}) : editFinalRoles.push({
            id: 2,
            role: "ROLE_ADMIN"
        })
    })
    return editFinalRoles
}

//TO FILL OUT THE MAIN TABLE
fetchAllUsers()

//TO DELETE A USER
let deleteID
const deleteModal = new bootstrap.Modal(document.querySelector('#deleteModal'));

on(document, 'click', '.deleteBtn', async e => {
    e.preventDefault()
    deleteModal.show()
    const tableTag = e.target.parentNode.parentNode
    deleteID = tableTag.firstElementChild.innerHTML
    const deleteFN = tableTag.querySelector('#allUsersFN').innerHTML
    const deleteLN = tableTag.querySelector('#allUsersLN').innerHTML
    const deleteAge = tableTag.querySelector('#allUsersAge').innerHTML
    const deleteEmail = tableTag.querySelector('#allUsersEmail').innerHTML
    const deleteRole = tableTag.querySelector('a').innerHTML.trim()
    let deleteRoles = deleteRole.split(' ')

    let delModalSelect = document.querySelector('.delModalSelect')
    delModalSelect.innerHTML = modalRoles(deleteRoles)

    let ID_Delete = document.querySelector('#ID_Delete')
    let FirstNameDelete = document.querySelector('#FirstNameDelete')
    let LastNameDelete = document.querySelector('#LastNameDelete')
    let AgeDelete = document.querySelector('#AgeDelete')
    let EmailDelete = document.querySelector('#EmailDelete')

    ID_Delete.placeholder = deleteID
    FirstNameDelete.placeholder = deleteFN
    LastNameDelete.placeholder = deleteLN
    AgeDelete.placeholder = deleteAge
    EmailDelete.placeholder = deleteEmail
})

on(document, 'click', '.delModalBtn', async e => {
    e.preventDefault()
    fetch(URL + deleteID, {
        method: 'DELETE'
    })
        .then(data => {
            console.log(data)
            if (data.status === 200) {
                let element = document.getElementById(deleteID)
                element.parentNode.removeChild(element)
                tableBody = document.querySelector('.allUsers')
            }
        })
    deleteModal.hide()
})


function modalRoles(deleteRoles) {
    let options = ''
    deleteRoles.forEach(role => {
        options += `<option>${role}</option>`
    })
    return options
}

//TO EDIT A USER
let editID
const editModal = new bootstrap.Modal(document.querySelector('#editModal'));
let ID_Edit, FirstNameEdit, LastNameEdit, AgeEdit, EmailEdit, PasswordEdit

on(document, 'click', '.editBtn', async e => {
    e.preventDefault()
    editModal.show()
    const editTableTag = e.target.parentNode.parentNode
    editID = editTableTag.firstElementChild.innerHTML
    const editFN = editTableTag.querySelector('#allUsersFN').innerHTML
    const editSN = editTableTag.querySelector('#allUsersLN').innerHTML
    const editAge = editTableTag.querySelector('#allUsersAge').innerHTML
    const editEmail = editTableTag.querySelector('#allUsersEmail').innerHTML
    const editPassword = editTableTag.querySelector('#allUsersPassword').innerHTML

    ID_Edit = document.querySelector('#editID')
    FirstNameEdit = document.querySelector('#editFN')
    LastNameEdit = document.querySelector('#editSN')
    AgeEdit = document.querySelector('#editAge')
    EmailEdit = document.querySelector('#editEmail')
    PasswordEdit = document.querySelector('#editPassword')

    ID_Edit.value = editID
    FirstNameEdit.value = editFN
    LastNameEdit.value = editSN
    AgeEdit.value = editAge
    EmailEdit.value = editEmail
    PasswordEdit.value = editPassword
})

on(document, 'click', '.editModalBtn', async e => {
    e.preventDefault()

    fetch(URL, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: editID,
            email: EmailEdit.value,
            password: PasswordEdit.value,
            firstName: FirstNameEdit.value,
            surname: LastNameEdit.value,
            age: AgeEdit.value,
            roles: selectedOptions()
        })
    })
        .then(res => {
            res.json()
                .then(data => {
                    let replaced = document.getElementById(editID)
                    const replacing = document.createElement('tr');
                    replacing.id = editID
                    replacing.innerHTML = insertedTRFunc(data)
                    replaced.parentNode.replaceChild(replacing, replaced)
                })
        })
    editModal.hide()
})


//TO CREATE USER
const createNewUserTab = document.querySelector("#createNewUserTab")

let firstNameCreate = document.querySelector('#createFN')
let lastNameCreate = document.querySelector('#createLN')
let ageCreate = document.querySelector('#createAge')
let emailCreate = document.querySelector('#createEmail')
let passwordCreate = document.querySelector('#createPassword')

createNewUserTab.addEventListener('click', e => {
    firstNameCreate.value = ''
    lastNameCreate.value = ''
    ageCreate.value = ''
    emailCreate.value = ''
    passwordCreate.value = ''
})

on(document, 'click', '.createNewUser', async e => {
    e.preventDefault()

    const createSelect = document.querySelector('.createSelect')

    let editFinalRoles = []
    const createSelectVal = [].filter.call(createSelect.options, option => option.selected)
        .map(option => option.text)
    createSelectVal.forEach((chosen) => {
        chosen === 'USER' ? editFinalRoles.push({id: 1, role: "ROLE_USER"}) : editFinalRoles.push({
            id: 2,
            role: "ROLE_ADMIN"
        })
    })

    fetch(URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            email: emailCreate.value,
            password: passwordCreate.value,
            firstName: firstNameCreate.value,
            surname: lastNameCreate.value,
            age: ageCreate.value,
            roles: editFinalRoles
        })
    }).then(res => res.json())
        .then(user => {
            tableBody.innerHTML = tableBody.innerHTML + insertedTRFunc(user)
        })
    let usersTable = document.querySelector('#nav-home-tab')
    let tab = new bootstrap.Tab(usersTable)
    tab.show()
})


// INFO OF LOGGED USER
let tableLoggedAdmin = document.querySelector('.loggedUser')
let usernameLogged = document.querySelector('.spanLoggedUser')
let rolesLoggedAdmin = document.querySelector('.authRoles')

fetch(URL + 'loggedUser', {
    method: 'GET'
})
    .then(res => res.json())
    .then((user) => {
        usernameLogged.textContent = user.username
        rolesLoggedAdmin.innerHTML = userRoles(user)
        tableLoggedAdmin.innerHTML = `<tr>
                           <td>${user.id}</td>
                           <td>${user.firstName}</td>
                           <td>${user.surname}</td>
                           <td>${user.age}</td>
                           <td>${user.email}</td>`
            + userRoles(user) + `</tr>`
    })




