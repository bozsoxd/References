import { HttpClient } from '@angular/common/http';
import { Component, inject, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'MitigiaFront';

  private apiUrl = 'http://localhost:8080/display'
  vehicles: any = [];
  http = inject(HttpClient)

  
  ngOnInit() {
    this.getVehicles();
    
    
  }

  getVehicles() {
    this.http.get<any>(this.apiUrl)
      .subscribe(vehicles => {
        this.vehicles = vehicles;
      });
  }

}


