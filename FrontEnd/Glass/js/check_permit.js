    // 권한 체크하여 메뉴리스트 만들기
    document.getElementById('loginForm').addEventListener('submit', function(e) {
      e.preventDefault(); // 폼 기본 제출 방지
  
      const formData = {
        username: document.getElementById('username').value,
        password: document.getElementById('password').value
      };
  
      fetch('/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData)
      })
      .then(response => response.json())
      .then(data => {
        console.log('Success:', data);
        checkPermissions(data.permissions); // 사용자 권한 확인 함수 호출
      })
      .catch((error) => {
        console.error('Error:', error);
      });
    });
  
    function checkPermissions(permissions) {
      // 예시 권한 확인 로직
      if (permissions.includes('admin')) {
        // 특정 권한이 있는 경우, 메뉴에 항목 추가
        const menuList = document.querySelector('#menu ul');
        const newMenuItem = document.createElement('li');
        const link = document.createElement('a');
        link.href = '/admin';
        link.textContent = '관리자 페이지';
        newMenuItem.appendChild(link);
        menuList.appendChild(newMenuItem);
      }
    }