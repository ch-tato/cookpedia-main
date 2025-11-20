function attachRemoveButtons() {
    document.querySelectorAll(".remove-btn").forEach(btn => {
        btn.removeEventListener("click", removeRow); // mencegah double event
        btn.addEventListener("click", removeRow);
    });
}

function removeRow(e) {
    const row = e.target.closest(".ingredient-row, .step-row");
    if (row) row.remove();
}

function addIngredient(name = "", quantity = "") {
    const div = document.createElement("div");
    div.className = "ingredient-row";
    div.innerHTML = `
        <input type="text" name="ingredientName[]" placeholder="Ingredient name" required value="${name}" />
        <input type="text" name="quantity[]" placeholder="Quantity" required value="${quantity}" />
        <button type="button" class="remove-btn">X</button>
    `;
    div.querySelector(".remove-btn").addEventListener("click", removeRow);
    document.getElementById("ingredients").appendChild(div);
}

function addStep(instruction = "") {
    const div = document.createElement("div");
    div.className = "step-row";
    div.innerHTML = `
        <textarea name="instruction[]" placeholder="Step instruction" required>${instruction}</textarea>
        <button type="button" class="remove-btn">X</button>
    `;
    div.querySelector(".remove-btn").addEventListener("click", removeRow);
    document.getElementById("steps").appendChild(div);
}

attachRemoveButtons();
