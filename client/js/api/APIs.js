class APIKalida extends APIBase{

    static API_IDENTIFIER = 'com_kalida_'
    static NICK_NAME = 'nickname'
    static TEXT_SEARCH = 'textSearch'
    static IMG_PERFIL = 'imgPerfil'
    static CLIENT_ID = 'clientId'

    constructor(){super('http://localhost:8081')}

    getInfo = apiKey => localStorage.getItem(APIKalida.API_IDENTIFIER+apiKey)
    setInfo = (apiKey, value) => localStorage.setItem(APIKalida.API_IDENTIFIER+apiKey, value)
    removeInfo = apiKey => localStorage.removeItem(APIKalida.API_IDENTIFIER+apiKey)

    getImagePerfil = () => JSON.parse(this.getInfo(APIKalida.IMG_PERFIL))
    setImagePerfil = imagePerfil => this.setInfo(APIKalida.IMG_PERFIL, JSON.stringify(imagePerfil))
    removeImagePerfil = () => this.removeInfo(APIKalida.IMG_PERFIL)

    getUserId = () => this.getUser().userId_

    getClientId = () => JSON.parse(this.getInfo(APIKalida.CLIENT_ID))
    setClientId = clientId => this.setInfo(APIKalida.CLIENT_ID, clientId)
    removeClientId = () => this.removeInfo(APIKalida.CLIENT_ID)

    toAuth = fragment => this.toResource('auth', fragment)
    toUsers = fragment => this.toResource('users', fragment)
    toNotification = fragment => this.toResource('notification', fragment)
    toSearch = fragment => this.toResource('search', fragment)
    toImgPerfil = fragment => this.toResource('img_perfil', fragment)
    toTecnology = fragment => this.toResource('technology', fragment)
    toExperiences = fragment => this.toResource('experience', fragment)
}

class APIViewKalida extends APIBase{

    static API_IDENTIFIER = 'com_kalida_view_'

    constructor(){super('http://localhost:5500')}

    getInfo = apiKey => localStorage.getItem(APIViewKalida.API_IDENTIFIER+apiKey)
    setInfo = (apiKey, value) => localStorage.setItem(APIViewKalida.API_IDENTIFIER+apiKey, value)
    removeInfo = apiKey => localStorage.removeItem(APIViewKalida.API_IDENTIFIER+apiKey)
    
}

const API_KALIDA = new APIKalida()
const API_VIEW_KALIDA = new APIViewKalida()
