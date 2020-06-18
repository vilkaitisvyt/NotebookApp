window.addEventListener("load", () => {
  let modalForm = document.getElementById("modalForm");
  let editModalForm = document.getElementById("editModalForm");
  let holder = document.getElementById("holder");

  modalForm.addEventListener("submit", (event) => {
    event.preventDefault();
  });
  editModalForm.addEventListener("submit", (event) => {
    event.preventDefault();
  });
  holder.addEventListener("submit", (event) => {
    event.preventDefault();
  });
});
