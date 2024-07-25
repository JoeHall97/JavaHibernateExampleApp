import json 
import random
import xml.etree.cElementTree as ET
from datetime import datetime
from os import path, makedirs

def read_suburb_data(filename: str) -> dict[str,list[str]]:
    with open(filename) as f:
        data = json.load(f)
        return data

def create_xml_output(location: str, suburbs: list[str]) -> ET.ElementTree:
    root = ET.Element("Location", name = location)
    start_date = datetime.strptime('1960-01-01', '%Y-%m-%d')
    end_date = datetime.now()

    for s in suburbs:
        suburb_xml = ET.SubElement(root, "Suburb", name = s)
        random_temp = round(random.uniform(-30.0, 60.0), 1)
        random_date = start_date + (end_date - start_date) * random.random()
        ET.SubElement(suburb_xml, "Temperature").text = str(random_temp)
        ET.SubElement(suburb_xml, "Date").text = str(random_date.date())
        
    return ET.ElementTree(root)

if __name__ == "__main__":
    suburb_data = read_suburb_data("../../data/suburbs.json")

    for city, suburbs in suburb_data.items():
        xml = create_xml_output(city, suburbs)
        folder = f'../../data/output/{city}'
        if not path.exists(folder):
            makedirs(folder)
        xml.write(f'{folder}/output_{datetime.now()}.xml')