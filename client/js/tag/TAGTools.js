//rever a inclusão desse cara no body, tá meio confuso
class Dialog extends JView{
    constructor(message, title, onConfirm, onNegate) {
        super({message, title, onConfirm, onNegate}).mount_()
    }

    _init() {
        A.div('view').a(
            A.div('divDialog').a([
                A.p('textTitle').t(this.title),
                A.p('textMessage').t(this.message),
                A.div('groupButtons').a([
                    A.inputButton('btnConfirm').v(LANG?.yes || 'Yes'),
                    A.inputButton('btnNegate').v(LANG?.not || 'Not')
                ])
            ])
        )

        this.styles()
    }

    show() { appendTo(body(), this)}

    remove() { this.disapend_()}

    setConfirmText(text){
        this.$btnConfirm.v(text)
        return this
    }

    setNegateText(text){
        this.$btnNegate.v(text)
        return this
    }

    hideBtnNegate(textToBtnConfirm){
        textToBtnConfirm && this.setConfirmText(textToBtnConfirm)
        hide(this.$btnNegate)
        return this
    }

    hideBtnConfirm(textToBtnNegate){
        textToBtnNegate && this.setNegateText(textToBtnNegate);
        hide(this.$btnConfirm)
        return this
    }

    _events() {
        this.$btnConfirm.onclick = () => {
            this.onConfirm?.()
            this.remove()
        };
        this.$btnNegate.onclick = () => {
            this.onNegate?.()
            this.remove()
        }
    }

    styles(){
        this.$view.style = `
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            grid-template-rows: repeat(3, 1fr);
            position: fixed !important;
            top: 0 !important;
            left: 0 !important;
            width: 100vw !important;
            height: 100vh !important;
            background-color: rgba(0,0,0,.5) !important;
        `
        
        this.$divDialog.style = `
            grid-area: 2 / 2 / span 1/ span 1;
            border: 4px solid wheat !important;
            border-radius: 10px !important;
            box-shadow: 2px 2px blue !important;
            min-width: 250px !important;
            max-width: 400px !important;
            min-height: 100px !important;
            max-height: 300px !important;
            background-color: white !important;
        `
        this.$textTitle.style = `
            font-size: 13pt;
            border-bottom: 1px solid aquamarine !important;
            padding: 8px 15px 3px !important;
            text-align: center !important;
            color: blue !important;
        
        `
        this.$textMessage.style = `
            text-indent: 15px !important;
            color: black !important;
            background-color: #eee !important;
            padding: 5px !important;
            margin: 4px !important;
            text-align: justify !important;
            border-radius: 3px !important;
        `
        
        this.$groupButtons.style = `
            margin: 8px 5px 18px !important;
        `
        this.$groupButtons.querySelectorAll('input')
            .forEach(input_ => input_.style = `
                    padding: 5px 18px;
                    margin: auto;
                    font-weight: bold;
                    float: right;
                    margin-left: 15px;
                    margin-bottom: 6px;
                `
        )
    }

}

class Waiter extends JView{

    constructor(message, elementSource, onchange){
        super({message, elementSource, onchange}).mount_()
    }

    _init(){
        A.div('view').a(
            A.div('divContent').a(
                A.p('textMessage').t(this.message),
                A.div('divAnimation')
            )
        )
        this.styles()
    }

    wait(condition = true) {
        if(!this.waiting && condition){
            removeChildren(this.$divAnimation)
            appendTo(body(), this)
            this.waiting = true
            if(this.elementSource){
                this.elementSource.waiting = true
                this.onchange?.(this.elementSource, true)
            }
            this.animation?.()
        }
    }

    free(){ 
        if(this.waiting){
            removeChildren(this.$divAnimation)
            this.disapend_()
            this.waiting = false
            if(this.elementSource) this.elementSource.waiting = false
            this.onchange?.(this.elementSource, false)
        }
    }

    showDivAnimation() {show(this.$divAnimation); return this}

    hideDivAnimation() {hide(this.$divAnimation); return this}

    showTextMessage() {show(this.$textMessage); return this}

    hideTextMessage() {hide(this.$textMessage); return this}

    setMessage(message){ this.message = message}
    
    animation(){
        let counter = 0;
        this.message && setInterval(() => this.$textMessage.t(this.message + ('...'.slice(-counter++ % 3))), 500);
    }

    setAnimation(animation){
        this.animation = animation;
        return this
    }

    styles(){
        this.$view.style = `
            z-index:1000 !important;
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            grid-template-rows: repeat(3, 1fr);
            position: fixed !important;
            top: 0 !important;
            left: 0 !important;
            width: 100vw !important;
            height: 100vh !important;
            background-color: rgba(0,0,0,.5) !important;
        `
        this.$textMessage.style = `
            padding-left: 1rem;
        `
        
        this.$divContent.style = `
            grid-area: 2 / 2 / span 1/ span 1;
            display: flex;
            flex-direction: column;
            border: 4px solid wheat !important;
            border-radius: 10px !important;
            box-shadow: 2px 2px blue !important;
            min-width: 250px !important;
            max-width: 400px !important;
            min-height: 100px !important;
            max-height: 300px !important;
            background-color: white !important; 
        `
        this.$divAnimation.style = `
            flex-grow: 1;
            background-color: red;
        `
    }

}

class WordSearch{
	
	constructor(word){
        this.len = word?.length
		this._generateNodes(word)
	}
	
	_generateNodes(word){
		if(word){
			word = word.toLowerCase()
			this.next = {index:0, value: word[0]}
			let iterator = this.next;
			for (let i = 1; i < word.length; i++){
				iterator.next = {index: i, value:word[i]}
				iterator = iterator.next
			}
		}
	}
	
	match(word){
		let source = this.next;
		if(source && word){
            let dest = new WordSearch(word),
            sourceLen = this.len,
            destLen = dest.len;
			while(dest && source){
                if(sourceLen - source.index > destLen - dest.index) return false
				if(source.value == dest.value) source = source.next
				dest = dest.next
            }
			return !source
		}
	}
}

class TextFormat {

    static userName(inputText) {
        inputText.addEventListener('keypress', event => {
            let key = event.key;
            key && !/[\w_]/.test(key) && consume(event)
        });
    }
    
    static name(inputText, eventName = 'keypress'){
        inputText.addEventListener(eventName, event => {
            let key = event.key;
            TextFormat._consumeIfReprove(event, 
                key && !/[A-Z ]/i.test(key),
                (/ /.test(key) && inputText.value.length) === 0,
                (/ $/.test(inputText.value) && / /.test(key))
            )
        })
    }
    
    static nickName(inputText, eventName = 'keypress'){
        inputText.addEventListener(eventName, event => {
            let key = event.key;
            TextFormat._consumeIfReprove(event, 
                key && !/[\w]/.test(key) && event.preventDefault(),
                (/_/.test(key) && inputText.value.length === 0) && consume(event),
                (/\d/.test(key) && inputText.value.length === 0) && consume(event),
                (/_$/.test(inputText.value) && /_/.test(key)) && consume(event)
            )
        })
    }

    static _consumeIfReprove = (event, ... tests) => tests.some(reproved => reproved) && consume(event)
    
    static onlyNumbers(inputText, eventName = 'keypress'){
        inputText.addEventListener(eventName, event => {
            let key = event.key;
            key && !/[\d]/.test(key) && consume(event)
        })
    }

    static acceptKeys(inputText, eventName, ... regex){
        inputText.addEventListener(eventName, event => {
            let key = event.key;
            regex.some(reg => !reg.test(key)) && consume(event)
        })
    }

    static rejectKeys(inputText, eventName, ... regex){
        inputText.addEventListener(eventName, event => {
            let key = event.key;
            regex.some(reg => reg.test(key)) && consume(event)
        })
    }
}

function windowSize() {
    let info = E.p().t('window:( ?, ?)');
    info.style = `
        background-color: black;
        color: white;
        font-size: 1.2rem;
        position: fixed;
        margin: 0;
        top: 95vh;
        z-index: 1000;
    `
    appendTo(body(), info);
    window.addEventListener('resize', _ => info.t(`window (${window.innerWidth}, ${window.innerHeight})`))
}

//rever as funções
let trackingRect;
function elementRect(element, shine) {
    if (trackingRect) {
        return;
    }
    trackingRect = true;
    function info() {
        let dado = element.getBoundingClientRect();
        let retorno = {
            'x0': dado.left,
            'x1': dado.right,
            'y0': dado.top.toFixed(2),
            'y1': dado.bottom.toFixed(2),
            largura: dado.right - dado.left,
            altura: dado.bottom - dado.top,
            windowHeigth: window.innerHeight,
            windowWidth: window.innerWidth
        };
        let eClass = element.getAttribute('class');
        retorno.literal = 'Tracking: '
                + (eClass ? eClass : element)
                + ': (x0 left , y0 top) = [' + retorno.x0 + ' , ' + retorno.y0
                + '], (x1 right , y1 bottom) = [' + retorno.x1 + ' , ' + retorno.y1
                + '][largura vs altura: ' + retorno.largura + ' x ' + retorno.altura + ']\n'
                + 'window width x heigth [' + retorno.windowWidth + ',' + retorno.windowHeigth + ']';
        return retorno;
    }

    let ePrint = E.p();
    ePrint.vagalume = true;
    ePrint.style = `background-color:black;color:white;font-size:1.2rem; position:fixed; margin: 0; top: 1px; z-index:1000`;
    ePrint.onclick = _ => ePrint.style.display = 'none'
    
    window.addEventListener('resize', function () {
        let inf = info();
        ePrint.innerHTML = inf.literal;
        if (shine) {
            element.style.border = '1px solid ' + (ePrint.vagalume ? 'red' : 'black');
            ePrint.vagalume = !ePrint.vagalume;
            show(ePrint);
        }
    });
    insertBefore(body().firstChild, ePrint);
}

let tagGetMousePosition;
let tag_counter = 0;
function monitoreBodyMousePosition() {

    if (!tagGetMousePosition) {
        tagGetMousePosition = {'x': 0,
            'y': 0
        };
        document.body.onmousemove = function (e) {
            selector('#metrica').innerHTML = ' - c' + tag_counter++;
            tagGetMousePosition.x = e.clientX;
            tagGetMousePosition.y = e.clientY;
            tagGetMousePosition.literal = 'Mouse position: (x,y)[' + tagGetMousePosition.x + ',' + tagGetMousePosition.y + ']';
        };
    }
}
//não está verificando se o elemento está escondido
function isMouseOver(element) {

    if (element.style) {
        let pos = elementRect(element);
        let x = tagGetMousePosition.x;
        let y = tagGetMousePosition.y;
        if (pos.x0 <= x && x <= pos.x1) return pos.y0 <= y && y <= pos.y1;
    }
    return false;
}

function getAbsolutCoords(element){
    let x = 0, y = 0,
        e = viewOrNode(element),
        {left, top, right, bottom, width, height} = e.getBoundingClientRect();

    while (e && !isNaN(e.offsetLeft) && !isNaN(e.offsetTop)) {
        x += e.offsetLeft - e.scrollLeft
        y += e.offsetTop - e.scrollTop
        e = e.offsetParent
    }
    right = x + right - left;
    bottom = y + bottom - top;
    return {x, y, left, top, right, bottom, width, height};
}


//não deve ficar na lib TAG ou derivados
async function sha256(message){
    //encode to utf-8
    const msgBuffer = new TextEncoder().encode(message),
        //hash the message
        hashBuffer = await crypto.subtle.digest('SHA-256', msgBuffer),
        //convert ArrayBuffer to Array
        hashArray = Array.from(new Uint8Array(hashBuffer)),
        //convert bytes to hex string
        hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');
    return hashHex

}