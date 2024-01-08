const listUsersURL = 'http://localhost:8080/admin/users';
const adminURL = 'http://localhost:8080/admin/getInfoAboutAdmin';
const newUserURL = 'http://localhost:8080/admin/newUser';
const findUserURL = 'http://localhost:8080/admin/';
const editUserURL = 'http://localhost:8080/admin/editUser';
const deleteUserURL = 'http://localhost:8080/admin/deleteUser/';

function getAllUsers() {
    fetch(listUsersURL)
        .then(res => res.json())
        .then(data => {
            loadTable(data)
        })
}

function loadTable(listAllUsers) {
    let res = ``;
    for (let user of listAllUsers) {
        res +=
            `<tr>
                <td>${user.id}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>${user.roles.map(r => r.role.replace('ROLE_', '')).join(' ')}</td>
                <td>
                    <button class="btn btn-sm btn-primary" type="button"
                    data-bs-toggle="modal" data-bs-target="#editModal"
                    onclick="editModal(${user.id})">Edit</button></td>
                <td>
                    <button class="btn btn-sm btn-danger" type="button"
                    data-bs-toggle="modal" data-bs-target="#deleteModal"
                    onclick="deleteModal(${user.id})">Delete</button></td>
            </tr>`
    }
    document.getElementById('tableBodyAdmin').innerHTML = res;
}

function newUserTab() {
    document.getElementById('newUserForm').addEventListener('submit', (e) => {
        e.preventDefault()
        let role = document.getElementById('rolesNew')
        let rolesAddUser = []
        for (let i = 0; i < role.options.length; i++) {
            if (role.options[i].selected) {
                rolesAddUser.push({id: role.options[i].value, role: 'ROLE_' + role.options[i].innerHTML})
            }
        }
        fetch(newUserURL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify({
                firstName: document.getElementById('newFirstName').value,
                lastName: document.getElementById('newLastName').value,
                age: document.getElementById('newAge').value,
                email: document.getElementById('newEmail').value,
                password: document.getElementById('newPassword').value,
                roles: rolesAddUser
            })
        })
            .then((response) => {
                if (response.ok) {
                    document.getElementById('newFirstName').value = '';
                    document.getElementById('newLastName').value = '';
                    document.getElementById('newAge').value = '';
                    document.getElementById('newEmail').value = '';
                    document.getElementById('newPassword').value = '';
                    document.getElementById('userTable-tab').click()
                    getAllUsers();
                }
            })
    })
}

function closeModal() {
    document.querySelectorAll(".btn-close").forEach((btn) => btn.click())
}


function editModal(id) {
    let editId = findUserURL + id;
    fetch(editId, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => {
        res.json().then(user => {
            document.getElementById('editId').value = user.id;
            document.getElementById('editFirstName').value = user.firstName;
            document.getElementById('editLastName').value = user.lastName;
            document.getElementById('editAge').value = user.age;
            document.getElementById('editEmail').value = user.email;
            document.getElementById('editPassword').value = '';
        })
    });
    loadRolesForEdit();
}

function loadRolesForEdit() {
    let selectEdit = document.getElementById("edit-roles");
    selectEdit.innerHTML = "";

    fetch("http://localhost:8080/admin/roles")
        .then(res => res.json())
        .then(data => {
            data.forEach(role => {
                let option = document.createElement("option");
                option.value = role.id;
                option.text = role.role.replace('ROLE_', '');
                selectEdit.appendChild(option);
            });
        })
        .catch(error => console.error(error));
}
window.addEventListener("load", loadRolesForEdit);


async function editUser() {
    let idValue = document.getElementById('editId').value;
    let firstNameValue = document.getElementById('editFirstName').value;
    let lastNameValue = document.getElementById('editLastName').value;
    let ageValue = document.getElementById('editAge').value;
    let emailValue = document.getElementById('editEmail').value;
    let passwordValue = document.getElementById('editPassword').value;
    let role = document.getElementById('editRole')
    // let listOfRole = []
    // for (let i = 0; i < role.options.length; i++) {
    //     if (role.options[i].selected) {
    //         listOfRole.push({id: role.options[i].value, name: 'ROLE_' + role.options[i].innerHTML})
    //     }
    // }
    let rolesForEdit = [];
    for (let i = 0; i < document.forms["modalEdit"].roles.options.length; i++) {
        if (document.forms["modalEdit"].roles.options[i].selected) rolesForEdit.push({
            id: document.forms["modalEdit"].roles.options[i].value,
            role: "ROLE_" + document.forms["modalEdit"].roles.options[i].text
        });
    }
    let user = {
        id: idValue,
        firstName: firstNameValue,
        lastName: lastNameValue,
        age: ageValue,
        email: emailValue,
        password: passwordValue,
        roles: rolesForEdit
    }
    await fetch(editUserURL, {
        method: 'PATCH',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify(user)
    });
    closeModal()
    getAllUsers()
}


function deleteModal(id) {
    let delId = findUserURL + id;
    fetch(delId, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => {
        res.json().then(user => {
            document.getElementById('deleteId').value = user.id;
            document.getElementById('deleteFirstName').value = user.firstName;
            document.getElementById('deleteLastName').value = user.lastName;
            document.getElementById('deleteAge').value = user.age;
            document.getElementById('deleteEmail').value = user.email;
        })
    });
}

async function deleteUser() {

    let urlDel = deleteUserURL + document.getElementById('deleteId').value;

    let method = {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    }

    fetch(urlDel, method).then(() => {
        closeModal()
        getAllUsers()
    })
}

function getUserPage() {
    let url2 = adminURL
    fetch(url2).then(response => response.json()).then(user =>
        getInformationAboutUser(user))
}

function getInformationAboutUser(user) {
    user.roles.map(r => {
        if (r.role.replace('ROLE_', '') === 'ADMIN') {
            getAllUsers()
            newUserTab()
        }
    })
    document.getElementById('userTableBody').innerHTML = `<tr>
            <td>${user.id}</td>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>${user.roles.map(r => r.role.replace('ROLE_', '')).join(' ')}</td>
        </tr>`;
}

getUserPage();