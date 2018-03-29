import {BeheerNg2Page} from './app.po';

describe('beheer-ng2 App', () => {
    let page: BeheerNg2Page;

    beforeEach(() => {
        page = new BeheerNg2Page();
    });

    it('should display message saying app works', () => {
        page.navigateTo();
        page.getParagraphText().then(text => expect(text).toEqual('app works'));
    });
});
