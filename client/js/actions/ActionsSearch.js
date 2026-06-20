class ActionsSearch extends JMount{
    constructor(actionsMenu) {
        super({viewMenu:actionsMenu.viewMenu}).mount_()
        this.searchEvent()
    }

    _init(){
        this.viewPageSearch = new ViewPageSearch();
    }

    _events(){
        this.eventCloseSearch()
    }

    searchEvent() {
        let {$inputSearch, $iconSearch} = this.viewMenu;
        $inputSearch.onsearch = () => this.search($inputSearch);
        $iconSearch.onclick = () => this.search($inputSearch);
    }

    search(inputSearch) {
        let fragment = {
            subPath:'find_tema_and_analise',
            terms:inputSearch.value
        }
        JRequest.prepare(API_KALIDA.toSearch(fragment)).
            inResponse(
                searchs => {
                    this.viewPageSearch.clearSearchs()
                    if(!this.viewPageSearch.showing){
                        this.viewPageSearch.showing = true
                        body().a(this.viewPageSearch)
                    }
                    jsonInMatrix(searchs).forEach(search => {
                        let viewRowSearch = new ViewRowSearch(search)
                        this.viewPageSearch.addViewRowSearch(viewRowSearch)
                    });
                },
                error => new Aspect(error)
            ).get()
    }

    eventCloseSearch(){
        this.viewPageSearch.$spanClose.onclick = _ =>{
            disapend(this.viewPageSearch)
            this.viewPageSearch.showing = false
        }
    }
}