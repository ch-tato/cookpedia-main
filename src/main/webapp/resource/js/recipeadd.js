function addIngredient() {
    const div = document.createElement("div");
    div.className = "ingredient-row";
    div.innerHTML = `
        <input type="text" name="ingredientName[]" placeholder="Ingredient name" required />
        <input type="text" name="quantity[]" placeholder="Quantity (ex: 100 grams)" required />
        <button type="button" class="remove-btn" onclick="this.parentElement.remove()">X</button>
        <br><br>
    `;
    document.getElementById("ingredients").appendChild(div);
}

function addStep() {
    const div = document.createElement("div");
    div.className = "step-row";
    div.innerHTML = `
        <textarea name="instruction[]" placeholder="Step instruction" required></textarea>
        <button type="button" class="remove-btn" onclick="this.parentElement.remove()">X</button>
        <br><br>
    `;
    document.getElementById("steps").appendChild(div);
}

const dropzone = document.getElementById("dropzone");
const imageInput = document.getElementById("imageInput");
const previewImg = document.getElementById("previewImg");
const cancelBtn = document.getElementById("cancelImageBtn");

dropzone.addEventListener("click", () => imageInput.click());

imageInput.addEventListener("change", function () {
    if (this.files && this.files[0]) {
        showPreview(this.files[0]);
    }
});

dropzone.addEventListener("dragover", function (e) {
    e.preventDefault();
    dropzone.classList.add("dragover");
});

dropzone.addEventListener("dragleave", function () {
    dropzone.classList.remove("dragover");
});

dropzone.addEventListener("drop", function (e) {
    e.preventDefault();
    dropzone.classList.remove("dragover");

    const file = e.dataTransfer.files[0];
    if (file) {
        imageInput.files = e.dataTransfer.files;
        showPreview(file);
    }
});

function showPreview(file) {
    const reader = new FileReader();
    reader.onload = function (e) {
        previewImg.src = e.target.result;
        previewImg.style.display = "block";
        dropzone.querySelector("p").style.display = "none";
        cancelBtn.style.display = "inline-block";
    };
    reader.readAsDataURL(file);
}

cancelBtn.addEventListener("click", () => {
    imageInput.value = "";
    previewImg.src = "";
    previewImg.style.display = "none";
    dropzone.querySelector("p").style.display = "block";
    cancelBtn.style.display = "none";
});