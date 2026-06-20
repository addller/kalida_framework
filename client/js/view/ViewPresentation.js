class ViewPresentation extends JView{
    constructor(entity){
        super(entity).mount_()
        this._selectPresentation()
    }

    _init(){
        A.iframe('iframePresentation')
    }

    _selectPresentation(){
        let enderessable =  new Enderessable(API_VIEW_KALIDA.toUrlBase(`/presentation_${LANG.cod_lang}.html`));
        this.$iframePresentation.src = enderessable.urlDest
    }

}