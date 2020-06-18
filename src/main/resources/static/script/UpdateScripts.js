const editModalForm = {
  id: null,
  entry: null,
};

function editEntry(id, entry) {
  editModalForm.id = id;
  document.getElementById("editEntry").value = entry;
}

function updateEntry() {
  console.log("updating entry with id " + editModalForm.id);
  editModalForm.entry = document.getElementById("editEntry");
  let token = localStorage.getItem("currentUser");
  let requestData = `id=${editModalForm.id}&updatedEntry=${editModalForm.entry.value}`;

  let request = new XMLHttpRequest();

  request.open("put", "/entryUpdate");
  request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  request.setRequestHeader("Authorization", "Bearer " + token);
  request.onload = () => {
    if (request.status === 403) {
      logout();
    }
    document.getElementById("holder").innerHTML = request.responseText;
  };

  if (token) {
    request.send(requestData);
  } else {
    getLoginPage();
  }
}
