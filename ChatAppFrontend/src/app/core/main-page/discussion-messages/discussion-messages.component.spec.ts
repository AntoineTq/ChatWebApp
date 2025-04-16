import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DiscussionMessagesComponent } from './discussion-messages.component';

describe('DiscussionMessagesComponent', () => {
  let component: DiscussionMessagesComponent;
  let fixture: ComponentFixture<DiscussionMessagesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DiscussionMessagesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DiscussionMessagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
