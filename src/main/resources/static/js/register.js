
window.onload = function() {

	// テキストボックスのDOMを取得
	const userName = document.getElementById("userName");

	// テキストボックスのDOMを取得
	const userEmail = document.getElementById("userEmail");
	
	// テキストボックスのDOMを取得
	const password = document.getElementById("password");

	// 活性/非活性を切り替えるボタンのDOMを取得
	const button = document.getElementById("register");

	// 入力テキストのキーアップイベント
	userName.addEventListener('keyup', function() {
		
		textInput();
	})

	// 入力テキストのキーアップイベント
	userEmail.addEventListener('keyup', function() {
		
		textInput();
	})
	
	// 入力テキストのキーアップイベント
	password.addEventListener('keyup', function() {
		
		textInput();
	})
	
	function textInput(){
		
		// テキストボックスに入力された値を取得
		const text1 = userName.value;
		const text2 = userEmail.value;
		const text3 = password.value;
				
		// テキストが入力されている場合
		if (text1) {
			if (text2) {
				if(text3){
							
					// ボタンのdisabled属性を取り除く
					button.disabled = null;
				} else{
					// ボタンにdisabledを設定する
					button.disabled = "disabled";
				}
			} else {			
				// ボタンにdisabledを設定する
				button.disabled = "disabled";
			}
		} else {
			// ボタンにdisabledを設定する
			button.disabled = "disabled";
		}
	}
}
