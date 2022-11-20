class ViewPresentation extends JView{
    constructor(entity){
        super(entity).mount_()
        this._selectPresentation()
    }

    _init(){
        A.iframe('iframePresentation')
    }

    _selectPresentation(){
        this.$iframePresentation.src = new Enderessable(API_VIEW_KALIDA.toUrlBase(`/presentation_${LANG.cod_lang}.html`)).urlDest;
    }

}