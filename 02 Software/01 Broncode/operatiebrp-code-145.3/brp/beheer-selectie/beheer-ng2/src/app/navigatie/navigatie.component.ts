import {Component, OnInit} from '@angular/core';
import {trigger, state, style, animate, transition} from '@angular/animations';

@Component({
  selector: 'app-navigatie',
  templateUrl: './navigatie.component.html',
  styleUrls: ['./navigatie.component.scss'],
  animations: [
    trigger('navigationState', [
      state('true', style({ opacity: 1, transform: 'scale(1.0)', display: 'block' })),
      state('false', style({ opacity: 0, transform: 'scale(0.0)', display: 'none' })),
      transition('true => false', animate('900ms')),
      transition('false => true', animate('300ms'))
    ])
  ]
})
export class NavigatieComponent implements OnInit {

  public showNavigation = 'true';

  constructor() {
  }

  ngOnInit() {
  }

  toggle() {
    this.showNavigation === 'true' ? this.showNavigation = 'false' : this.showNavigation = 'true';
  }
}
