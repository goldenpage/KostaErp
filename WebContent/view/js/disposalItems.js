function loadData(page) {
    const category = document.getElementById("categoryFilter").value;
    const reason = document.getElementById("reasonFilter").value;
    const p = page || 1;

    const url = "controller?cmd=disposalUIAction&ajax=true&category=" 
                + encodeURIComponent(category) + "&reason=" + encodeURIComponent(reason) + "&page=" + p;

    const xhr = new XMLHttpRequest();
    xhr.open("GET", url, true);
    xhr.withCredentials = true; 
    
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const tempDiv = document.createElement("div");
            tempDiv.innerHTML = xhr.responseText;

            // 1. 테이블 본문 교체 
            const newRows = tempDiv.querySelector("#ajaxTableData tbody");
            if(newRows) {
                document.querySelector(".list_container tbody").innerHTML = newRows.innerHTML;
            }

            // 2. 페이징 데이터 교체
            const newPaging = tempDiv.querySelector("#ajaxPagingData");
            if(newPaging) {
                document.querySelector(".page").innerHTML = newPaging.innerHTML;
            }
        }
    };
    xhr.send();
}

// 정렬 로직
var sortDirectionMap = {};
function sortTable(n, btn) {
    var table = document.querySelector(".list_container tbody");
    var rows = Array.from(table.querySelectorAll("tr"));
    sortDirectionMap[n] = !sortDirectionMap[n];
    var asc = sortDirectionMap[n];

    rows.sort(function(a, b) {
        var A = a.cells[n].innerText.trim();
        var B = b.cells[n].innerText.trim();
        if (!isNaN(A.replace(/,/g, "")) && !isNaN(B.replace(/,/g, ""))) {
            A = Number(A.replace(/,/g, ""));
            B = Number(B.replace(/,/g, ""));
        }
        return A < B ? (asc ? -1 : 1) : (A > B ? (asc ? 1 : -1) : 0);
    });
    rows.forEach(function(row){ table.appendChild(row); });
    updateIcons(btn, asc);
}

function updateIcons(activeBtn, asc) {
    var buttons = document.querySelectorAll(".list_container th button");
    buttons.forEach(function(b) {
        b.innerText = "▼";
    });
    activeBtn.innerText = asc ? "▲" : "▼";
}

function resetFilter(){
    document.getElementById("categoryFilter").value = "전체";
    document.getElementById("reasonFilter").value = "전체";
    loadData(1);
}

function updateReason(disposalId) {
    const selectBox = document.getElementById("reason_" + disposalId);
    const reasonId = selectBox.value; 

    if(!confirm("해당 항목의 폐기 사유를 수정하시겠습니까?")) return;
	
    const url = "controller?cmd=disposalUIAction&disposalId=" + disposalId + "&reasonId=" + reasonId;

    const xhr = new XMLHttpRequest();
    xhr.open("GET", url, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            if(xhr.responseText.trim() === "true") {
                alert("성공적으로 수정되었습니다.");
                const activePage = document.querySelector(".page a.active") ? document.querySelector(".page a.active").innerText : 1;
                loadData(activePage); 
            } else {
                alert("수정에 실패했습니다.");
            }
        }
    };
    xhr.send();
}