package com.example.employeems.service;

import com.example.employeems.dto.EmployeeDTO;
import com.example.employeems.entity.Employee;
import com.example.employeems.repo.EmployeeRepo;
import com.example.employeems.util.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo;
    //save method
    @Autowired
    private ModelMapper modelMapper;

    public String saveEmployee(EmployeeDTO employeeDTO){
        //check already availability
        if(employeeRepo.existsById(employeeDTO.getEmpID())){
            return VarList.RSP_DUPLICATED;
        }else{
                employeeRepo.save(modelMapper.map(employeeDTO, Employee.class));
                return VarList.RSP_SUCCESS;
        }
    }

    //update method
    public String updateEmployee(EmployeeDTO employeeDTO){
        if(employeeRepo.existsById(employeeDTO.getEmpID())){
            employeeRepo.save(modelMapper.map(employeeDTO,Employee.class));
            return  VarList.RSP_SUCCESS;
        }else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    //get all data
    public List<EmployeeDTO>getAllEmployee(){
        List<Employee>employeeList=employeeRepo.findAll();
        return modelMapper.map(employeeList,new TypeToken<ArrayList<EmployeeDTO>>(){
        }.getType());
    }

    //search
    public EmployeeDTO searchEmployee(int empID){
        if(employeeRepo.existsById(empID)){
            Employee employee=employeeRepo.findById(empID).orElse(null);
            return modelMapper.map(employee,EmployeeDTO.class);
        } else{
            return null;
        }
    }

    //delete
    public String deleteEmployee(int empID){
        if(employeeRepo.existsById(empID)){
            employeeRepo.deleteById(empID);
            return VarList.RSP_SUCCESS;
        }else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

}
