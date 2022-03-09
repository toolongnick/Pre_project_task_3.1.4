// INFO OF LOGGED USER
const URL = '/api/people/'
let tableLoggedUser = document.querySelector('.loggedUser')
let usernameLoggedUser = document.querySelector('.spanLoggedUser')
let rolesLoggedUser = document.querySelector('.authRoles')


let userRoles = (user) => {
    let roleInnerHTML = ''
    user.roles.forEach(role => {
        roleInnerHTML += (role.role === 'ROLE_USER' ? 'USER ' : 'ADMIN ')
    })
    return `<td>` + `<a>${roleInnerHTML}</a>` + `</td>`
}

fetch(URL + 'loggedUser', {
    method: 'GET'
})
    .then(res => res.json())
    .then((user) => {
        usernameLoggedUser.textContent = user.username
        rolesLoggedUser.innerHTML=  userRoles(user)
        tableLoggedUser.innerHTML = `<tr>
                           <td>${user.id}</td>
                           <td>${user.firstName}</td>
                           <td>${user.surname}</td>
                           <td>${user.age}</td>
                           <td>${user.email}</td>`
            + userRoles(user) + `</tr>`
        console.log(tableLoggedUser)
    })